package GUI.controller;

import GUI.models.ModelsHandler;

public abstract class BaseController {
    public ModelsHandler getModelsHandler() throws Exception {
        return ModelsHandler.getInstance();
    }
}
