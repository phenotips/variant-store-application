package org.phenotips.variantStoreIntegration.jobs;

import org.phenotips.Constants;
import org.phenotips.data.Patient;

import org.xwiki.model.EntityType;
import org.xwiki.model.reference.EntityReference;
import org.xwiki.observation.ObservationManager;

import java.util.concurrent.Future;

import javax.inject.Inject;

import com.xpn.xwiki.XWiki;
import com.xpn.xwiki.XWikiContext;
import com.xpn.xwiki.XWikiException;
import com.xpn.xwiki.doc.XWikiDocument;
import com.xpn.xwiki.objects.BaseObject;
import com.xpn.xwiki.objects.StringProperty;

/**
 * Created by matthew on 2015-04-24.
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
