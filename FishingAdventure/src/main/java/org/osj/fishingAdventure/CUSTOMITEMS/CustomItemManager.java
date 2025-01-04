package org.osj.fishingAdventure.CUSTOMITEMS;

import dev.lone.itemsadder.api.CustomStack;
import dev.lone.itemsadder.api.Events.ItemsAdderLoadDataEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.osj.fishingAdventure.FISHING_SYSTEM.FishingManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class CustomItemManager implements Listener
{
    private List<CustomStack> normalFishList = new ArrayList<>();
    private List<CustomStack> rareFishList = new ArrayList<>();
    private List<CustomStack> ancientFishList = new ArrayList<>();
    private List<CustomStack> overlordFishList = new ArrayList<>();
    private List<CustomStack> legendFishList = new ArrayList<>();
    private List<CustomStack> mythFishList = new ArrayList<>();
    private List<CustomStack> allFishList = new ArrayList<>();

    // 전리품 확률
    static List<CustomStack> itemInFishList60 = new ArrayList<>();
    static List<CustomStack> itemInFishList25 = new ArrayList<>();
    static List<CustomStack> itemInFishList10 = new ArrayList<>();

    // 낚싯대
    CustomStack normalRod;
    CustomStack rareRod;
    CustomStack ancientRod;
    CustomStack overloadRod;
    CustomStack legendRod;
    CustomStack mythRod;

    // 열쇠
    CustomStack normal_key;
    CustomStack rare_key;
    CustomStack ancient_key;
    CustomStack overload_key;
    CustomStack legend_key;
    CustomStack myth_key;

    // 수족관
    CustomStack ordinary_aquarium;
    CustomStack delicate_aquarium;
    CustomStack environmental_reproduction_aquarium;
    CustomStack shock_absorption_aquarium;
    CustomStack folklores_sealing_tool;
    CustomStack mythical_sanctuary;

    // 잼스톤
    CustomStack normalGemStone;
    CustomStack rareGemStone;
    CustomStack ancientGemStone;
    CustomStack overloadGemStone;
    CustomStack legendGemStone;
    CustomStack mythGemStone;
    CustomStack durabilityGemStone;
    CustomStack durabilityHealGemStone;

    // 티켓 종류
    CustomStack chunkPurchaseTicket;
    CustomStack chunkRemoveTicket;
    CustomStack wildTeleportTicket;
    CustomStack inviteTicket;

    // gui
    CustomStack fish_shadow;
    CustomStack icon_right_blue;
    CustomStack icon_left_blue;

    // 일반급 종
    CustomStack goldfish;
    CustomStack carp;
    CustomStack guppy;
    CustomStack betta;
    CustomStack catfish;
    CustomStack ricefish;
    CustomStack herring;
    CustomStack mackerel;
    CustomStack largehead_hairtail;
    CustomStack salmon;
    CustomStack trout;
    CustomStack eel;
    CustomStack red_seabream;
    CustomStack olive_flounder;
    CustomStack rockfish;
    CustomStack sea_bass;
    CustomStack jack_mackerel;
    CustomStack anchovy;
    CustomStack clupeidae;
    CustomStack yellowback_seabream;
    CustomStack ara;
    CustomStack seabream;
    CustomStack spanish_mackerel;
    CustomStack obtuse_barracuda;
    CustomStack mackerel_pike;
    CustomStack pufferfish;
    CustomStack flathead_grey_mullet;
    CustomStack marbled_rockfish;
    CustomStack tuna;
    CustomStack mitre_squid;
    CustomStack goby;
    CustomStack parrotfish;
    CustomStack plankton;
    CustomStack krill;

    // 희귀급 종
    CustomStack mola_mola;
    CustomStack sea_snake;
    CustomStack human_face_fish;
    CustomStack clione;
    CustomStack blobfish;
    CustomStack grimpoteuthis;
    CustomStack himantolophus_groenlandicus;
    CustomStack macrocheira_kaempferi;
    CustomStack electric_eel;
    CustomStack giant_squid;
    CustomStack sphyran_zygaena;
    CustomStack striped_marlin;
    CustomStack mobula_birostris;
    CustomStack stygiomedusa_gigantea;
    CustomStack eschrichtius_robustus;
    CustomStack hincodon_typus;
    CustomStack great_white_shark;
    CustomStack orcinus_orca;
    CustomStack sperm_whale;
    CustomStack balaenoptera_musculus;

    // 고대급 종
    CustomStack stromatolite;
    CustomStack trilobite;
    CustomStack ammonite;
    CustomStack sacabamba;
    CustomStack orthoceras;
    CustomStack archelon;
    CustomStack helicoprion_bessonowi;
    CustomStack ichthyosaurus;
    CustomStack elasmosaurus;
    CustomStack dunkleosteus;

    // 패왕급 종
    CustomStack anomalocaris;
    CustomStack deinosuchus;
    CustomStack mosasaurus;
    CustomStack liopleurodon_ferox;
    CustomStack livyatan_melvillei;
    CustomStack kronosaurus;
    CustomStack otodus_megalodon;

    // 전설급 종
    CustomStack sky_whale;
    CustomStack aspidochelone;
    CustomStack moby_dick;
    CustomStack kraken;
    CustomStack bloop;

    // 신화급 종
    CustomStack jormungand;
    CustomStack charybdis;
    CustomStack leviathan;

    @EventHandler
    public void onItemsAdderLoaded(ItemsAdderLoadDataEvent event)
    {
        fish_shadow = CustomStack.getInstance("fishing_adventure:fish_shadow");
        icon_right_blue = CustomStack.getInstance("fishing_adventure:icon_right_blue");
        icon_left_blue = CustomStack.getInstance("fishing_adventure:icon_left_blue");
        loadRods();
        loadKeys();
        loadAquariums();
        LoadGemStones();
        LoadTickets();
        makeItemInFishList();
        LoadNormalFishes();
        LoadRareFishes();
        LoadAncientFishes();
        LoadOverloadFishes();
        LoadLegendFishes();
        LoadMythFishes();
        makeAllFishList();
    }

    private void loadRods()
    {
        normalRod = CustomStack.getInstance("fishing_adventure:normal_rod");
        rareRod = CustomStack.getInstance("fishing_adventure:rare_rod");
        ancientRod = CustomStack.getInstance("fishing_adventure:ancient_rod");
        overloadRod = CustomStack.getInstance("fishing_adventure:overload_rod");
        legendRod = CustomStack.getInstance("fishing_adventure:legend_rod");
        mythRod = CustomStack.getInstance("fishing_adventure:myth_rod");
    }

    private void loadKeys()
    {
        normal_key = CustomStack.getInstance("fishing_adventure:normal_key");
        rare_key = CustomStack.getInstance("fishing_adventure:rare_key");
        ancient_key = CustomStack.getInstance("fishing_adventure:ancient_key");
        overload_key = CustomStack.getInstance("fishing_adventure:overload_key");
        legend_key = CustomStack.getInstance("fishing_adventure:legend_key");
        myth_key = CustomStack.getInstance("fishing_adventure:myth_key");
    }

    private void loadAquariums()
    {
        ordinary_aquarium = CustomStack.getInstance("fishing_adventure:ordinary_aquarium");
        delicate_aquarium = CustomStack.getInstance("fishing_adventure:delicate_aquarium");
        environmental_reproduction_aquarium = CustomStack.getInstance("fishing_adventure:environmental_reproduction_aquarium");
        shock_absorption_aquarium = CustomStack.getInstance("fishing_adventure:shock_absorption_aquarium");
        folklores_sealing_tool = CustomStack.getInstance("fishing_adventure:folklores_sealing_tool");
        mythical_sanctuary = CustomStack.getInstance("fishing_adventure:mythical_sanctuary");
    }

    private void LoadGemStones()
    {
        normalGemStone = CustomStack.getInstance("fishing_adventure:normal_gemstone");
        rareGemStone = CustomStack.getInstance("fishing_adventure:rare_gemstone");
        ancientGemStone = CustomStack.getInstance("fishing_adventure:ancient_gemstone");
        overloadGemStone = CustomStack.getInstance("fishing_adventure:overload_gemstone");
        legendGemStone = CustomStack.getInstance("fishing_adventure:legend_gemstone");
        mythGemStone = CustomStack.getInstance("fishing_adventure:myth_gemstone");
        durabilityGemStone = CustomStack.getInstance("fishing_adventure:durability_gemstone");
        durabilityHealGemStone = CustomStack.getInstance("fishing_adventure:durability_heal_gemstone");
    }
    private void LoadTickets()
    {
        chunkPurchaseTicket = CustomStack.getInstance("fishing_adventure:chunkpurchaseticket");
        chunkRemoveTicket = CustomStack.getInstance("fishing_adventure:chunkremoveticket");
        wildTeleportTicket = CustomStack.getInstance("fishing_adventure:wildteleportticket");
        inviteTicket = CustomStack.getInstance("fishing_adventure:inviteticket");
    }
    private void LoadNormalFishes()
    {
        krill = CustomStack.getInstance("fishing_adventure:krill");
        plankton = CustomStack.getInstance("fishing_adventure:plankton");
        mitre_squid = CustomStack.getInstance("fishing_adventure:mitre_squid");
        anchovy = CustomStack.getInstance("fishing_adventure:anchovy");
        clupeidae = CustomStack.getInstance("fishing_adventure:clupeidae");
        ricefish = CustomStack.getInstance("fishing_adventure:ricefish");
        guppy = CustomStack.getInstance("fishing_adventure:guppy");
        goldfish = CustomStack.getInstance("fishing_adventure:goldfish");
        betta = CustomStack.getInstance("fishing_adventure:betta");
        goby = CustomStack.getInstance("fishing_adventure:goby");
        carp = CustomStack.getInstance("fishing_adventure:carp");
        jack_mackerel = CustomStack.getInstance("fishing_adventure:jack_mackerel");
        pufferfish = CustomStack.getInstance("fishing_adventure:pufferfish");
        mackerel_pike = CustomStack.getInstance("fishing_adventure:mackerel_pike");
        largehead_hairtail = CustomStack.getInstance("fishing_adventure:largehead_hairtail");
        obtuse_barracuda = CustomStack.getInstance("fishing_adventure:obtuse_barracuda");
        flathead_grey_mullet = CustomStack.getInstance("fishing_adventure:flathead_grey_mullet");
        eel = CustomStack.getInstance("fishing_adventure:eel");
        parrotfish = CustomStack.getInstance("fishing_adventure:parrotfish");
        marbled_rockfish = CustomStack.getInstance("fishing_adventure:marbled_rockfish");
        yellowback_seabream = CustomStack.getInstance("fishing_adventure:yellowback_seabream");
        trout = CustomStack.getInstance("fishing_adventure:trout");
        olive_flounder = CustomStack.getInstance("fishing_adventure:olive_flounder");
        rockfish = CustomStack.getInstance("fishing_adventure:rockfish");
        mackerel = CustomStack.getInstance("fishing_adventure:mackerel");
        herring = CustomStack.getInstance("fishing_adventure:herring");
        spanish_mackerel = CustomStack.getInstance("fishing_adventure:spanish_mackerel");
        salmon = CustomStack.getInstance("fishing_adventure:salmon");
        catfish = CustomStack.getInstance("fishing_adventure:catfish");
        sea_bass = CustomStack.getInstance("fishing_adventure:sea_bass");
        red_seabream = CustomStack.getInstance("fishing_adventure:red_seabream");
        seabream = CustomStack.getInstance("fishing_adventure:seabream");
        ara = CustomStack.getInstance("fishing_adventure:ara");
        tuna = CustomStack.getInstance("fishing_adventure:tuna");

        makeNormalFishList();
    }
    private void LoadRareFishes()
    {
        mola_mola = CustomStack.getInstance("fishing_adventure:mola_mola");
        sea_snake = CustomStack.getInstance("fishing_adventure:sea_snake");
        human_face_fish = CustomStack.getInstance("fishing_adventure:human_face_fish");
        clione = CustomStack.getInstance("fishing_adventure:clione");
        blobfish = CustomStack.getInstance("fishing_adventure:blobfish");
        grimpoteuthis = CustomStack.getInstance("fishing_adventure:grimpoteuthis");
        himantolophus_groenlandicus = CustomStack.getInstance("fishing_adventure:himantolophus_groenlandicus");
        macrocheira_kaempferi = CustomStack.getInstance("fishing_adventure:macrocheira_kaempferi");
        electric_eel = CustomStack.getInstance("fishing_adventure:electric_eel");
        giant_squid = CustomStack.getInstance("fishing_adventure:giant_squid");
        sphyran_zygaena = CustomStack.getInstance("fishing_adventure:sphyran_zygaena");
        striped_marlin = CustomStack.getInstance("fishing_adventure:striped_marlin");
        mobula_birostris = CustomStack.getInstance("fishing_adventure:mobula_birostris");
        stygiomedusa_gigantea = CustomStack.getInstance("fishing_adventure:stygiomedusa_gigantea");
        eschrichtius_robustus = CustomStack.getInstance("fishing_adventure:eschrichtius_robustus");
        hincodon_typus = CustomStack.getInstance("fishing_adventure:hincodon_typus");
        great_white_shark = CustomStack.getInstance("fishing_adventure:great_white_shark");
        orcinus_orca = CustomStack.getInstance("fishing_adventure:orcinus_orca");
        sperm_whale = CustomStack.getInstance("fishing_adventure:sperm_whale");
        balaenoptera_musculus = CustomStack.getInstance("fishing_adventure:balaenoptera_musculus");

        makeRareFishList();
    }
    private void LoadAncientFishes()
    {
        stromatolite = CustomStack.getInstance("fishing_adventure:stromatolite");
        trilobite = CustomStack.getInstance("fishing_adventure:trilobite");
        ammonite = CustomStack.getInstance("fishing_adventure:ammonite");
        orthoceras = CustomStack.getInstance("fishing_adventure:orthoceras");
        sacabamba = CustomStack.getInstance("fishing_adventure:sacabamba");
        archelon = CustomStack.getInstance("fishing_adventure:archelon");
        helicoprion_bessonowi = CustomStack.getInstance("fishing_adventure:helicoprion_bessonowi");
        ichthyosaurus = CustomStack.getInstance("fishing_adventure:ichthyosaurus");
        elasmosaurus = CustomStack.getInstance("fishing_adventure:elasmosaurus");
        dunkleosteus = CustomStack.getInstance("fishing_adventure:dunkleosteus");

        makeAncientFishList();
    }
    private void LoadOverloadFishes()
    {
        anomalocaris = CustomStack.getInstance("fishing_adventure:anomalocaris");
        deinosuchus = CustomStack.getInstance("fishing_adventure:deinosuchus");
        mosasaurus = CustomStack.getInstance("fishing_adventure:mosasaurus");
        liopleurodon_ferox = CustomStack.getInstance("fishing_adventure:liopleurodon_ferox");
        livyatan_melvillei = CustomStack.getInstance("fishing_adventure:livyatan_melvillei");
        kronosaurus = CustomStack.getInstance("fishing_adventure:kronosaurus");
        otodus_megalodon = CustomStack.getInstance("fishing_adventure:otodus_megalodon");

        makeOverloadFishList();
    }
    private void LoadLegendFishes()
    {
        sky_whale = CustomStack.getInstance("fishing_adventure:sky_whale");
        aspidochelone = CustomStack.getInstance("fishing_adventure:aspidochelone");
        moby_dick = CustomStack.getInstance("fishing_adventure:moby_dick");
        kraken = CustomStack.getInstance("fishing_adventure:kraken");
        bloop = CustomStack.getInstance("fishing_adventure:bloop");

        makeLegendFishList();
    }
    private void LoadMythFishes()
    {
        jormungand = CustomStack.getInstance("fishing_adventure:jormungand");
        charybdis = CustomStack.getInstance("fishing_adventure:charybdis");
        leviathan = CustomStack.getInstance("fishing_adventure:leviathan");

        makeMythFishList();
    }

    public static CustomStack makeCopy(CustomStack origin)
    {
        return CustomStack.getInstance(origin.getNamespacedID());
    }

    private void makeItemInFishList()
    {
        itemInFishList60.add(durabilityHealGemStone);
        itemInFishList25.add(durabilityGemStone);
        itemInFishList10.add(chunkPurchaseTicket);
    }

    public List<CustomStack> getKeyList()
    {
        return new ArrayList<>(Arrays.asList(normal_key, rare_key, ancient_key, overload_key, legend_key, myth_key));
    }

    public CustomStack getRod(int index)
    {
        switch (index)
        {
            case 1:
                return normalRod;
            case 2:
                return rareRod;
            case 3:
                return ancientRod;
            case 4:
                return overloadRod;
            case 5:
                return legendRod;
            case 6:
                return  mythRod;
            default:
                return null;
        }
    }

    public CustomStack getFishShadow()
    {
        return fish_shadow;
    }
    public CustomStack getIconRightBlue()
    {
        return icon_right_blue;
    }
    public CustomStack getIconLeftBlue()
    {
        return icon_left_blue;
    }

    public CustomStack getItemInFishList(int grade)
    {
        Random random = new Random();
        int randNum = random.nextInt(1, 101);
        if(randNum < 60)
        {
            return switch (grade)
            {
                case 1 -> normalGemStone;
                case 2 -> rareGemStone;
                case 3 -> ancientGemStone;
                case 4 -> overloadGemStone;
                case 5 -> legendGemStone;
                default -> mythGemStone;
            };
        }
        if(randNum < 60 + 20)
        {
            return durabilityHealGemStone;
        }
        else if(randNum < 60 + 20 + 10)
        {
            return switch (grade)
            {
                case 1 -> ordinary_aquarium;
                case 2 -> delicate_aquarium;
                case 3 -> environmental_reproduction_aquarium;
                case 4 -> shock_absorption_aquarium;
                case 5 -> folklores_sealing_tool;
                default -> mythical_sanctuary;
            };
        }
        else if(randNum < 60 + 20 + 10 + 5)
        {
            return durabilityGemStone;
        }
        else if(randNum < 60 + 20 + 10 + 5 + 4)
        {
            return chunkPurchaseTicket;
        }
        else
        {
            return switch (grade)
            {
                case 1 -> rare_key;
                case 2 -> ancient_key;
                case 3 -> overload_key;
                case 4 -> legend_key;
                case 5 -> myth_key;
                default -> normal_key;
            };
        }
    }

    public int getFishListSize()
    {
        return getAllFishList().size();
    }

    public int getFishNum(CustomStack fish)
    {
        List<CustomStack> fishList;
        int count = 0;
        switch (FishingManager.getFishGrade(fish.getPermission()))
        {
            case 1:
                fishList = getNormalFishList();
                count = 1;
                break;
            case 2:
                fishList = getRareFishList();
                count = 35;
                break;
            case 3:
                fishList = getAncientFishList();
                count = 55;
                break;
            case 4:
                fishList = getOverlordFishList();
                count = 65;
                break;
            case 5:
                fishList = getLegendFishList();
                count = 72;
                break;
            case 6:
                fishList = getMythFishList();
                count = 77;
                break;
            default:
                fishList = getUniqueFishList();
                break;
        }
        for(int i = fishList.size() - 1; i >= 0; i--)
        {
            if(fishList.get(i).getDisplayName().equals(fish.getDisplayName()))
            {
                return count;
            }
            count++;
        }
        return -1;
    }

    public int getFishIndex(CustomStack fish)
    {
        List<CustomStack> allFish = getAllFishList();
        for(int i = 0; i < allFish.size(); i++)
        {
            if(allFish.get(i).getDisplayName().equals(fish.getDisplayName()))
            {
                return i;
            }
        }
        return -1;
    }

    public void makeNormalFishList()
    {
        normalFishList.addFirst(krill);
        normalFishList.addFirst(plankton);
        normalFishList.addFirst(mitre_squid);
        normalFishList.addFirst(anchovy);
        normalFishList.addFirst(clupeidae);
        normalFishList.addFirst(ricefish);
        normalFishList.addFirst(guppy);
        normalFishList.addFirst(goldfish);
        normalFishList.addFirst(betta);
        normalFishList.addFirst(goby);
        normalFishList.addFirst(carp);
        normalFishList.addFirst(jack_mackerel);
        normalFishList.addFirst(pufferfish);
        normalFishList.addFirst(mackerel_pike);
        normalFishList.addFirst(largehead_hairtail);
        normalFishList.addFirst(obtuse_barracuda);
        normalFishList.addFirst(flathead_grey_mullet);
        normalFishList.addFirst(eel);
        normalFishList.addFirst(parrotfish);
        normalFishList.addFirst(marbled_rockfish);
        normalFishList.addFirst(yellowback_seabream);
        normalFishList.addFirst(trout);
        normalFishList.addFirst(olive_flounder);
        normalFishList.addFirst(rockfish);
        normalFishList.addFirst(mackerel);
        normalFishList.addFirst(herring);
        normalFishList.addFirst(spanish_mackerel);
        normalFishList.addFirst(salmon);
        normalFishList.addFirst(catfish);
        normalFishList.addFirst(sea_bass);
        normalFishList.addFirst(red_seabream);
        normalFishList.addFirst(seabream);
        normalFishList.addFirst(ara);
        normalFishList.addFirst(tuna);
    }
    public List<CustomStack> getNormalFishList()
    {
        return normalFishList;
    }
    public void makeRareFishList()
    {
        rareFishList.addFirst(mola_mola);
        rareFishList.addFirst(sea_snake);
        rareFishList.addFirst(human_face_fish);
        rareFishList.addFirst(clione);
        rareFishList.addFirst(blobfish);
        rareFishList.addFirst(grimpoteuthis);
        rareFishList.addFirst(himantolophus_groenlandicus);
        rareFishList.addFirst(macrocheira_kaempferi);
        rareFishList.addFirst(electric_eel);
        rareFishList.addFirst(giant_squid);
        rareFishList.addFirst(sphyran_zygaena);
        rareFishList.addFirst(striped_marlin);
        rareFishList.addFirst(mobula_birostris);
        rareFishList.addFirst(stygiomedusa_gigantea);
        rareFishList.addFirst(eschrichtius_robustus);
        rareFishList.addFirst(hincodon_typus);
        rareFishList.addFirst(great_white_shark);
        rareFishList.addFirst(orcinus_orca);
        rareFishList.addFirst(sperm_whale);
        rareFishList.addFirst(balaenoptera_musculus);
    }
    public List<CustomStack> getRareFishList()
    {
        return rareFishList;
    }
    public void makeAncientFishList()
    {
        ancientFishList.addFirst(stromatolite);
        ancientFishList.addFirst(trilobite);
        ancientFishList.addFirst(ammonite);
        ancientFishList.addFirst(sacabamba);
        ancientFishList.addFirst(orthoceras);
        ancientFishList.addFirst(archelon);
        ancientFishList.addFirst(helicoprion_bessonowi);
        ancientFishList.addFirst(ichthyosaurus);
        ancientFishList.addFirst(elasmosaurus);
        ancientFishList.addFirst(dunkleosteus);
    }
    public List<CustomStack> getAncientFishList()
    {
        return ancientFishList;
    }
    public void makeOverloadFishList()
    {
        overlordFishList.addFirst(anomalocaris);
        overlordFishList.addFirst(deinosuchus);
        overlordFishList.addFirst(mosasaurus);
        overlordFishList.addFirst(liopleurodon_ferox);
        overlordFishList.addFirst(livyatan_melvillei);
        overlordFishList.addFirst(kronosaurus);
        overlordFishList.addFirst(otodus_megalodon);
    }
    public List<CustomStack> getOverlordFishList()
    {
        return overlordFishList;
    }
    public void makeLegendFishList()
    {
        legendFishList.addFirst(sky_whale);
        legendFishList.addFirst(aspidochelone);
        legendFishList.addFirst(moby_dick);
        legendFishList.addFirst(kraken);
        legendFishList.addFirst(bloop);
    }
    public List<CustomStack> getLegendFishList()
    {
        return legendFishList;
    }
    public void makeMythFishList()
    {
        mythFishList.addFirst(jormungand);
        mythFishList.addFirst(charybdis);
        mythFishList.addFirst(leviathan);
    }
    public List<CustomStack> getMythFishList()
    {
        return mythFishList;
    }
    public List<CustomStack> getUniqueFishList()
    {
        List<CustomStack> uniqueFishList = new ArrayList<>();
        return uniqueFishList;
    }

    public void makeAllFishList()
    {
        allFishList.addAll(normalFishList);
        allFishList.addAll(rareFishList);
        allFishList.addAll(ancientFishList);
        allFishList.addAll(overlordFishList);
        allFishList.addAll(legendFishList);
        allFishList.addAll(mythFishList);
    }
    public List<CustomStack> getAllFishList()
    {
        return allFishList;
    }

    public boolean isFish(ItemStack fishStack)
    {
        CustomStack fish = CustomStack.byItemStack(fishStack);
        if(fish == null)
        {
            return false;
        }

        return switch (fish.getPermission())
        {
            case "ia.fishing_adventure.normal_fish", "ia.fishing_adventure.rare_fish",
                 "ia.fishing_adventure.ancient_fish", "ia.fishing_adventure.overload_fish",
                 "ia.fishing_adventure.legend_fish", "ia.fishing_adventure.myth_fish",
                 "ia.fishing_adventure.unique_fish" -> true;
            default -> false;
        };
    }
}
