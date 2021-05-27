import java.util.*;

public class StableMatching {
    public static void main (String[] args) {
        System.out.println("Hello world");
        Map<String, List<String>> menPreference = new HashMap();
        Map<String, List<String>> wonmenPreference = new HashMap();

        menPreference.put("A", Arrays.asList(new String[]{"a", "b", "c"}));
        menPreference.put("B", Arrays.asList(new String[]{"a", "b", "c"}));
        menPreference.put("C", Arrays.asList(new String[]{"a", "b", "c"}));

        wonmenPreference.put("a", Arrays.asList(new String[]{"A", "B", "C"}));   
        wonmenPreference.put("b", Arrays.asList(new String[]{"A", "B", "C"}));        
        wonmenPreference.put("c", Arrays.asList(new String[]{"A", "B", "C"}));   
        
        Map<String, Map<String, Integer>> womenPreferRank = constructWomenPreferRank(wonmenPreference);
        System.out.println(womenPreferRank);

        Queue<String> freeMen = new LinkedList<>(menPreference.keySet());

        // women -> men 
        Map<String, String> pair = new HashMap();

        while (!freeMen.isEmpty()) {
            String man = freeMen.poll();
            // get his high ranked lady 
            for (String woman : menPreference.get(man)) {
                // if that lady is not engaged 
                if (!pair.containsKey(woman)) {
                    pair.put(woman, man);
                    break;
                } else {
                    String prevMan = pair.get(woman);
                    if (isPreferCurrentMan(woman, prevMan,man,womenPreferRank)) {
                        freeMen.add(prevMan);
                        pair.put(woman, man);
                        break;
                        }
                }
            }
        }

        System.out.println(pair);
    }

    public static Map<String, Map<String, Integer>> constructWomenPreferRank (
        Map<String, List<String>> wonmenPreference) {
            Map<String, Map<String, Integer>> womenPreferRank = new HashMap();
            
            for (String woman : wonmenPreference.keySet()) {
                List<String> rankList = wonmenPreference.get(woman);
                int rank = 0;
                Map<String, Integer> preferRank = new HashMap();
                for (String man : rankList) {
                    preferRank.put(man, rank++);
                }
                womenPreferRank.put(woman, preferRank);
            }
            return womenPreferRank;
        }

    public static boolean isPreferCurrentMan (
        String wonman, 
        String prevMan, 
        String currMan,
        Map<String, Map<String, Integer>> womenPreferRank) {
        Map<String, Integer> perferRank = womenPreferRank.get(wonman);
        if (perferRank.get(currMan) < perferRank.get(prevMan)) {
            return true;
        } else {
            return false;
        }
    }
}