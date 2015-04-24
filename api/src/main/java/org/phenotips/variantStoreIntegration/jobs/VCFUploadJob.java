/*
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.phenotips.variantStoreIntegration.jobs;

import org.phenotips.Constants;
import org.phenotips.data.Patient;
import org.phenotips.variantStoreIntegration.events.VCFRemovalCompleteEvent;
import org.phenotips.variantStoreIntegration.events.VCFUploadCompleteEvent;

import org.xwiki.model.EntityType;
import org.xwiki.model.reference.EntityReference;
import org.xwiki.observation.ObservationManager;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import com.xpn.xwiki.XWiki;
import com.xpn.xwiki.XWikiContext;
import com.xpn.xwiki.XWikiException;
import com.xpn.xwiki.doc.XWikiDocument;
import com.xpn.xwiki.objects.BaseObject;
import com.xpn.xwiki.objects.StringProperty;

/**
 * A runnable class to wrap the future returned from a call to Variant Store addPatient. Ensures that patient
 * information is synchronized with the status of the variant store.
 *
 * @version $Id$
 */
public class VCFUploadJob extends AbstractVCFJob
{


    /**
     * @param patient A PhenoTips Patient ID
     * @param variantStoreFuture The future returned by the variant store.
     * @param context The xwiki context
     * @param observationManager The observation manager for event pubs
     */
    public VCFUploadJob(Patient patient, Future variantStoreFuture, XWikiContext context,
        ObservationManager observationManager)
    {
        this.future = variantStoreFuture;
        this.patient = patient;
        this.context = context;
        this.observationManager = observationManager;
    }

    @Override
    public void run()
    {
        String propertyName = "status";
        try {
            // set patient VCF upload status to 'uploading' on disk
            this.setUploadStatus("processing", this.patient, this.context);

            this.future.get();

            // upon successful VCF upload set patient VCF upload status to 'Done' on disk
            this.setUploadStatus("complete", this.patient, this.context);

            this.observationManager.notify(new VCFUploadCompleteEvent(this.patient), this);

        } catch (InterruptedException e) {
            // variant store job was interrupted (canceled?) set VCF upload status to nothing.
            try {
                this.setUploadStatus("", this.patient, this.context);
                this.observationManager.notify(new VCFRemovalCompleteEvent(this.patient), this);
            } catch (XWikiException e1) {
                //TODO: Log error message
                e1.printStackTrace();
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
            //TODO: Log error message
        } catch (XWikiException e) {
            e.printStackTrace();
            //TODO: Log error message
        }
    }
}
