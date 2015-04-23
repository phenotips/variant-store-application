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
package org.phenotips.variantStoreIntegration.mocks;

import org.phenotips.variantstore.VariantStoreInterface;
import org.phenotips.variantstore.shared.VariantStoreException;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import org.ga4gh.GAVariant;

/**
 * A mock variant store.
 *
 * @version $Id$
 */
public class MockVariantStore implements VariantStoreInterface
{
    /**
     * Start up the variant store.
     */
    public void init()
    {
    }

    @Override
    public void init(Path path) throws VariantStoreException
    {

    }

    /**
     * Not used yet.
     */
    @Override
    public void stop()
    {
    };

    /**
     * @param id adsf
     * @param isPublic asdfas
     * @param file asdf
     * @return Future sdf
     */
    @Override
    public Future<Boolean> addIndividual(String id, boolean isPublic, Path file)
    {
        Callable<Boolean> task = new MockProcessingTask();
        return new FutureTask<Boolean>(task);
    }

    /**
     * @param id w/e
     * @return w/e
     */
    @Override
    public Future<Boolean> removeIndividual(String id)
    {
        Callable<Boolean> task = new MockProcessingTask();
        return new FutureTask<Boolean>(task);
    }

    @Override
    public List<GAVariant> getTopHarmfullVariants(String s, int i)
    {
        return null;
    }

    @Override
    public List<String> getIndividuals()
    {
        return null;
    }

    @Override
    public Map<String, List<GAVariant>> getIndividualsWithGene(String geneSymbol, List<String> variantEffects,
        Map<String, Double> alleleFrequencies)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, List<GAVariant>> getIndividualsWithVariant(String chr, int pos, String ref, String alt)
    {
        // TODO Auto-generated method stub
        return null;
    }

}
