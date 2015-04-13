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

/**
 * A class that listens for internal VCFEvents and pushes them to the XWiki {@link EventStream}.
 *
 * @version $Id$
 */
@Component
@Singleton
public class VCFEventToStreamAdapter implements EventListener, Initializable
{
    private static final String NAME = "VCFEventToStream";

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
        return VCFEventToStreamAdapter.NAME;
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
