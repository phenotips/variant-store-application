package org.phenotips.variantStoreIntegration;

import org.phenotips.data.Patient;
import org.phenotips.variantStoreIntegration.Events.VCFEvent;

import org.xwiki.component.annotation.Component;
import org.xwiki.component.phase.Initializable;
import org.xwiki.component.phase.InitializationException;
import org.xwiki.eventstream.EventFactory;
import org.xwiki.eventstream.EventStream;
import org.xwiki.observation.EventListener;
import org.xwiki.observation.ObservationManager;
import org.xwiki.observation.event.Event;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Component
@Singleton
public class VCFEventToStreamAdapter implements EventListener, Initializable
{
    private final String NAME = "VCFEventToStream";

    @Inject
    private ObservationManager om;

    @Inject
    private EventFactory streamEventFactory;

    @Inject
    private EventStream stream;

    @Override
    public void initialize() throws InitializationException
    {
        // Register self in observation manager

    }

    @Override
    public String getName()
    {
        return this.NAME;
    }

    @Override
    public List<Event> getEvents()
    {
        return Collections.<Event>singletonList(new Event()
        {

            @Override
            public boolean matches(Object otherEvent)
            {
                return otherEvent instanceof VCFEvent;
            }

        });
    }

    @Override
    public void onEvent(Event event, Object source, Object data)
    {
        if (!(event instanceof VCFEvent)) {
            return;
        }

        org.xwiki.eventstream.Event streamEvent = this.streamEventFactory.createEvent();
        Patient patient = ((VCFEvent) event).getPatient();
        streamEvent.setApplication("variant-store-integration");
        streamEvent.setType(((VCFEvent) event).getType());
        streamEvent.setDocument(patient.getDocument());

        this.stream.addEvent(streamEvent);
    }

}
