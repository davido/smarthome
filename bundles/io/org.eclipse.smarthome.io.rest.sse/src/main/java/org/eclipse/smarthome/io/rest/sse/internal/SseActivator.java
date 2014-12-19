/**
 * Copyright (c) 2014 openHAB UG (haftungsbeschraenkt) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.smarthome.io.rest.sse.internal;

import org.eclipse.smarthome.io.rest.sse.internal.async.BlockingAsyncFeature;
import org.eclipse.smarthome.io.rest.sse.internal.util.SseUtil;
import org.glassfish.jersey.media.sse.SseFeature;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Bundle activator for Eclipse Smarthome SSE bundle.
 * 
 * @author Ivan Iliev - Initial Contribution and API
 * 
 */
public class SseActivator implements BundleActivator {

    private static final Logger logger = LoggerFactory.getLogger(SseActivator.class);

    private static BundleContext context;

    private ServiceRegistration sseFeatureRegistration;

    private ServiceRegistration blockingAsyncFeatureRegistration;

    /**
     * Called whenever the OSGi framework starts our bundle
     */
    public void start(BundleContext bc) throws Exception {
        context = bc;

        String featureName = SseFeature.class.getName();
        if (bc.getServiceReference(featureName) == null) {
            sseFeatureRegistration = bc.registerService(featureName, new SseFeature(), null);

            logger.debug("SSE API - SseFeature registered.");
        }

        if (!SseUtil.SERVLET3_SUPPORT) {
            blockingAsyncFeatureRegistration = bc.registerService(BlockingAsyncFeature.class.getName(),
                    new BlockingAsyncFeature(), null);

            logger.debug("SSE API - SSE BlockingAsyncFeature registered.");
        }
        logger.debug("SSE API has been started.");
    }

    /**
     * Called whenever the OSGi framework stops our bundle
     */
    public void stop(BundleContext bc) throws Exception {
        context = null;

        if (sseFeatureRegistration != null) {
            sseFeatureRegistration.unregister();
            logger.debug("SseFeature unregistered.");
        }

        if (blockingAsyncFeatureRegistration != null) {
            blockingAsyncFeatureRegistration.unregister();
            logger.debug("BlockingAsyncFeature unregistered.");
        }

        logger.debug("SSE API has been stopped.");
    }

    /**
     * Returns the bundle context of this bundle
     * 
     * @return the bundle context
     */
    public static BundleContext getContext() {
        return context;
    }
}
