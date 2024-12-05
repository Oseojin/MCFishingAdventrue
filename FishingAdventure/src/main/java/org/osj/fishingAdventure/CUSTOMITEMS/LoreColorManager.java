package org.osj.fishingAdventure.CUSTOMITEMS;

import dev.lone.itemsadder.api.CustomStack;
import net.kyori.adventure.text.format.TextColor;

public class LoreColorManager
{
    public static TextColor fishingRodDurabilityColor = TextColor.color(0, 170, 170);
    public static TextColor fishingRodStrengthColor = TextColor.color(255, 85, 85);
    public static TextColor fishingRodStringColor = TextColor.color(170, 170, 170);
    public static TextColor fishingRodReelColor = TextColor.color(255, 255, 85);

    public static TextColor getGemStoneColor(int index)
    {
        TextColor textColor;
        switch(index)
        {
            case 0:
                textColor = TextColor.color(255, 3, 0);
                break;
            case 1:
                textColor = TextColor.color(157, 157, 157);
                break;
            case 2:
                textColor = TextColor.color(255, 244, 0);
                break;
            case 3:
                textColor = TextColor.color(0, 15, 255);
                break;
            case 4:
                textColor = TextColor.color(0, 232, 255);
                break;
            default:
                textColor = TextColor.color(0, 0, 0);
        }

        return textColor;
    }
}
