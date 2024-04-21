//For GUI testing
public class RaceGUITest {
        public static void main(String[] args) {
            NewHorse horse1 = new NewHorse('1', "Horse1", 0.5);
            NewHorse horse2 = new NewHorse('2', "Horse2", 0.5);
            NewHorse horse3 = new NewHorse('3', "Horse3", 0.5);
            NewHorse horse4 = new NewHorse('4', "Horse4", 0.5);
            NewHorse horse5 = new NewHorse('5', "Horse5", 0.5);
            NewHorse horse6 = new NewHorse('6', "Horse6", 0.5);
            NewHorse horse7 = new NewHorse('7', "Horse7", 0.5);
            NewHorse horse8 = new NewHorse('8', "Horse8", 0.5);
            NewHorse horse9 = new NewHorse('9', "Horse9", 0.5);
            NewHorse horse10 = new NewHorse('0', "Horse10", 0.5);
    
            NewHorse[] horses = {horse1, horse2, horse3, horse4, horse5, horse6, horse7, horse8, horse9, horse10};
    
            RaceGUI race = new RaceGUI(10, 10);
            for (int i = 0; i < 10; i++){
                race.addHorse(horses[i], i + 1);
            }
    
            race.startRace();
        }
}
    
