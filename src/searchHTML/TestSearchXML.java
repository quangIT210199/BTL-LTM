/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package searchHTML;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;


public class TestSearchXML {
    public static void main(String[] args) {
        String str = "<spell>\n" +
"    <name>Acid Splash</name>\n" +
"    <level>0</level>\n" +
"    <school>C</school>\n" +
"    <time>1 action</time>\n" +
"    <range>60 feet</range>\n" +
"    <components>V, S</components>\n" +
"    <duration>Instantaneous</duration>\n" +
"    <classes>Sorcerer, Wizard, Fighter (Eldritch Knight), Rogue (Arcane Trickster)</classes>\n" +
"    <text>You hurl a bubble of acid. Choose one creature within range, or choose two creatures within range that are within 5 feet of each other. A target must succeed on a Dexterity saving throw or take 1d6 acid damage.</text>\n" +
"    <text />\n" +
"    <text>This spells damage increases by 1d6 when you reach 5th Level (2d6), 11th level (3d6) and 17th level (4d6).</text>\n" +
"    <roll>1d6</roll>\n" +
"    <roll>2d6</roll>\n" +
"    <roll>3d6</roll>\n" +
"    <roll>4d6</roll>\n" +
"</spell>\n" +
"<spell>\n" +
"    <name>Aid</name>\n" +
"    <level>2</level>\n" +
"    <school>A</school>\n" +
"    <time>1 action</time>\n" +
"    <range>30 feet</range>\n" +
"    <components>V, S, M (a tiny strip of white cloth)</components>\n" +
"    <duration>8 hours</duration>\n" +
"    <classes>Artificer, Cleric, Paladin</classes>\n" +
"    <text>Your spell bolsters your allies with toughness and resolve. Choose up to three creatures within range. Each target's hit point maximum and current hit points increase by 5 for the duration.</text>\n" +
"    <text />\n" +
"    <text>At Higher Levels: When you cast this spell using a spell slot of 3rd level or higher, a target's hit points increase by an additional 5 for each slot level above 2nd.</text>\n" +
"</spell>";
        
        Document doc = Jsoup.parse(str, "", Parser.xmlParser());
        Elements elements = doc.getElementsByTag("spell");
        StringBuilder sb = new StringBuilder();
        for(Element e : elements) {
//            sb.append(e.text()).append(", ");
            System.out.println(e);
        }
        System.out.println(sb.toString());
    }
}
