package net.tognola.zensole;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;

public class SearchController_Test {

    private SearchController searchController;
    private ZenStore zenStoreMock;
    private ResultRenderer rendererMock;



    @Before
    public void setUp() {
        zenStoreMock = Mockito.mock(ZenStore.class);
        rendererMock = Mockito.mock(ResultRenderer.class);
        searchController = new SearchController(zenStoreMock, rendererMock);
    }



    @Test
    public void searchShouldDelegateToZenStore() {
        searchController.search();
        Mockito.verify(zenStoreMock).search();
    }



    @Test
    public void searchShouldDelegateToRenderer() {
        searchController.search();
        Mockito.verify(rendererMock).render(any());
    }

}
