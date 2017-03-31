package ru.alfabank.sonar.rpg;

import org.sonar.api.Plugin;

public class MyRpgCustomPlugin implements Plugin {

    @Override
    public void define(Context context) {
        context.addExtension(MyRpgRulesDefinition.class);
    }

}
