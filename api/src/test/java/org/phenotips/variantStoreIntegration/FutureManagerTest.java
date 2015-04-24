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

import org.phenotips.variantStoreIntegration.jobs.FutureManager;
import org.phenotips.variantStoreIntegration.upload.DefaultVCFUploadManager;

import org.xwiki.observation.event.Event;

import org.junit.Test;

import static org.mockito.Mockito.mock;

/**
 * Tests for the {@link DefaultVCFUploadManager} .
 *
 * @version $Id: f5805e3443d52c6e851b125b01713a2d1b7e774b $
 */
public class FutureManagerTest
{

    @Test
    public void testRemovalOnEvent()
    {
        Event removalEvent = mock(Event.class);
        FutureManager manager = new FutureManager("testManager", removalEvent);
    }
}
