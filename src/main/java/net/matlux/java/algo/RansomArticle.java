package net.matlux.java.algo;

import org.apache.commons.lang3.tuple.Pair;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RansomArticle {

    public boolean matchesWith(String ransom, String article) {


        Map<String, Integer>  ransomWordCount = countWords(ransom);


        final Map<String, Integer>  articleWordCount = countWords(article);

        ransomWordCount.entrySet().stream()
                .map(e -> Arrays.asList(e.getKey(),e.getValue(), articleWordCount.getOrDefault(e.getKey(),0),e.getValue() > articleWordCount.getOrDefault(e.getKey(),0)))
                .filter(ax -> (Boolean)ax.get(3))
                .collect(Collectors.toList());


        return ransomWordCount.entrySet().stream()
                .map(e -> e.getValue() > articleWordCount.getOrDefault(e.getKey(),0))
                .filter(b -> b).count() == 0;

    }


    public Map<String, Integer> countWords(String lines) {
        return Arrays.asList(lines.split(" |\\.|\\n")).stream().map(w -> w.toLowerCase())
                .collect(Collectors.toMap(Function.identity(), w -> 1,(c1 , c2) -> c1 + c2));


//                filterNot(_ == "").
//                map(w => (w.toLowerCase, 1)).
//        groupBy(_._1).
//                mapValues(l => l.map(p => p._2).sum)
    }
}
