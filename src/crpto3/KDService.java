package crpto3;
public class KDService {
    public static String calculate(double kills, double deaths, double wanted, double limit, double deathsLimit) {
        double result;
        if (deathsLimit == 0) {
            result = (wanted * deaths - kills) / limit;
            return "Kills in perfect games (0 deaths): " + result;
        } else {
            result = (wanted * (deaths + limit * deathsLimit) - kills) / (limit * deathsLimit);
            return "K/D each game: " + result + "\n(" + deathsLimit * result + " kills)";
        }
    }
}
