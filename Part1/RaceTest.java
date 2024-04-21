public class RaceTest {
    public static void main(String[] args) {
        Horse horse1 = new Horse('1', "Horse1", 0.5);
        Horse horse2 = new Horse('2', "Horse2", 0.5);
        Horse horse3 = new Horse('3', "Horse3", 0.5);
        Horse horse4 = new Horse('4', "Horse4", 0.5);
        Horse horse5 = new Horse('5', "Horse5", 0.5);
        Horse horse6 = new Horse('6', "Horse6", 0.5);
        Horse horse7 = new Horse('7', "Horse7", 0.5);
        Horse horse8 = new Horse('8', "Horse8", 0.5);
        Horse horse9 = new Horse('9', "Horse9", 0.5);
        Horse horse10 = new Horse('0', "Horse10", 0.5);

        Horse[] horses = {horse1, horse2, horse3, horse4, horse5, horse6, horse7, horse8, horse9, horse10};

        Race race = new Race(10, 10);
        for (int i = 0; i < 10; i++){
            race.addHorse(horses[i], i + 1);
        }

        race.startRace();
    }
}
