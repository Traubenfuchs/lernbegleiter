package at.technikumwien.lernbegleiter.controller;

import at.technikumwien.lernbegleiter.components.*;
import org.springframework.beans.factory.annotation.*;

public abstract class BaseController {
  @Autowired
  protected AuthHelper authHelper;
}