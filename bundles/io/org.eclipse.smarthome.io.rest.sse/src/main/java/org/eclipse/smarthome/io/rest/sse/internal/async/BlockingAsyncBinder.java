package org.eclipse.smarthome.io.rest.sse.internal.async;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.internal.inject.CustomAnnotationImpl;
import org.glassfish.jersey.servlet.spi.AsyncContextDelegateProvider;

/**
 * An {@link AbstractBinder} implementation that registers our custom
 * {@link BlockingAsyncContextDelegateProvider} class as an implementation of
 * the {@link AsyncContextDelegateProvider} SPI interface.
 * 
 * @author Ivan Iliev - Initial Contribution and API
 * 
 */
public class BlockingAsyncBinder extends AbstractBinder {

    @Override
    protected void configure() {
        // the qualifiedBy is needed in order for our implementation to be used
        // if there are multiple implementations of AsyncContextDelegateProvider
        bind(new BlockingAsyncContextDelegateProvider()).to(AsyncContextDelegateProvider.class).qualifiedBy(
                new CustomAnnotationImpl());
    }

}
