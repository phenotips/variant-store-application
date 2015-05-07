/*
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.phenotips.variantStoreIntegration.jobs;

import org.phenotips.Constants;
import org.phenotips.data.Patient;

import org.xwiki.model.EntityType;
import org.xwiki.model.reference.EntityReference;
import org.xwiki.observation.ObservationManager;

import java.util.concurrent.Future;

import com.xpn.xwiki.XWiki;
import com.xpn.xwiki.XWikiContext;
import com.xpn.xwiki.XWikiException;
import com.xpn.xwiki.doc.XWikiDocument;
import com.xpn.xwiki.objects.BaseObject;
import com.xpn.xwiki.objects.StringProperty;

/** Abstract class for VCF jobs
 * @version $Id$
 */
abstract class AbstractVCFJob implements Runnable
{
    public static final EntityReference STATUS_CLASS_REFERENCE = new EntityReference("VCFStatusClass", EntityType.DOCUMENT,
        Constants.CODE_SPACE_REFERENCE);
    protected Future future;

    protected Patient patient;

    protected XWikiContext context;

    protected ObservationManager observationManager;

    protected void setUploadStatus(String status, Patient patient, XWikiContext context) throws XWikiException
    {
        String propertyName = "status";
        XWiki xwiki = this.context.getWiki();
        XWikiDocument d = xwiki.getDocument(this.patient.getDocument(), this.context);
        BaseObject uploadStatusObject = d.getXObject(STATUS_CLASS_REFERENCE, true, this.context);

        StringProperty statusProp = (StringProperty) uploadStatusObject.get(propertyName);
        statusProp.setValue(status);
        xwiki.saveDocument(d, this.context);
    }
}
