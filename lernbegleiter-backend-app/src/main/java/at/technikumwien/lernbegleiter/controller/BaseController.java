package at.technikumwien.lernbegleiter.controller;

import at.technikumwien.lernbegleiter.components.AuthHelper;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseController   {
    @Autowired
    protected AuthHelper authHelper;
}
