import java.util.regex.Matcher

class Parser {
    static def tokens = []
    static String token
    int pos, posMax

    Parser(String vm){
        Matcher comments = vm =~ /\/\/.*/
        while (comments.find()){
            //remoção de comentários
            vm = vm.replace(comments.group(),"")
        }
        Matcher tks = vm =~ /[a-zA-Z][_a-zA-Z-|\/.|\/-|0-9]*|0|[1-9][0-9]*/
        while (tks.find()){
            //obtenção dos tokens
            tokens.add(tks.group())
        }
        pos = 0
        posMax = tokens.size() - 1
        token = tokens[0]
    }

    boolean hasMoreComands(){
        if(pos <= posMax)
            return true
        else
            return false
    }

    void advance(){
        pos++
        if(hasMoreComands()){

            token = tokens[pos]
        }
    }

    void restart(){
        pos = 0
    }

}
