package net.tognola.zensole;

public class SearchController {

    private final ZenStore zenStore;
    private final ResultRenderer renderer;



    public SearchController(ZenStore zenStore, ResultRenderer renderer) {
        this.zenStore = zenStore;
        this.renderer = renderer;
    }



    public String search() {
        return renderer.render(zenStore.search());
    }
}
