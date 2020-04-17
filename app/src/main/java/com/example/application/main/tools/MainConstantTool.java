package com.example.application.main.tools;

import androidx.annotation.IntDef;

import static com.example.application.main.tools.MainConstantTool.BEIJING;
import static com.example.application.main.tools.MainConstantTool.HANGZHOU;
import static com.example.application.main.tools.MainConstantTool.SHANGHAI;
import static com.example.application.main.tools.MainConstantTool.SHENZHEN;

@IntDef({SHANGHAI, HANGZHOU, BEIJING,SHENZHEN})
public @interface MainConstantTool {
    int SHANGHAI = 0;
    int HANGZHOU = 1;
    int BEIJING = 2;
    int SHENZHEN = 3;
}
