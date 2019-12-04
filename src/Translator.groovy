class Translator {
    static wrt = []
    static p = []

    static void main(String[] args){
        def dh = new File(args[0])
        def vmfiles = []
        try {
            dh.eachFile {
                if (it.toString().endsWith(".vm")) {
                    vmfiles.addAll(new String(it.getBytes()))
                    wrt.add( new CodeWriter(it.toString()))
                }
            }
            for (def s : vmfiles) {
                p.add(new Parser(s))
            }
        }catch (Exception e){
            p.add(new Parser(new String(dh.getBytes())))
            wrt.add(new CodeWriter(dh.toString()))
        }

        getComands()
    }

    static void getComands(){
        int i = 0
        for(parser in p) {
            CodeWriter write = wrt[i]
            while (parser.hasMoreComands()) {
//                write.writeInit()
                switch (parser.token) {
//                    case "return":
//                        write.writeReturn()
//                        break
//                    case "label":
//                        parser.advance()
//                        write.writeLabel(parser.token)
//                        break
//                    case "if-goto":
//                        parser.advance()
//                        write.writeIf(parser.token)
//                        break
                    case "push":
                        parser.advance()
                        String segment = parser.token
                        parser.advance()
                        write.writePush(segment,parser.token)
                        break
                    case "pop":
                        parser.advance()
                        String segment = parser.token
                        parser.advance()
                        write.writePop(segment,parser.token)
                        break
//                    case "function":
//                        parser.advance()
//                        String name = parser.token
//                        parser.advance()
//                        write.writeFunction(name,parser.token)
//                        break
//                    case "call":
//                        parser.advance()
//                        String name = parser.token
//                        parser.advance()
//                        write.writeCall(name,parser.token)
//                        break
//                    case "goto":
//                        parser.advance()
//                        write.writeGoto(parser.token)
//                        break
                    case "add":
                        write.writeArithmetic(parser.token)
                        break
                    case"sub":
                        write.writeArithmetic(parser.token)
                        break
                    case"neg":
                        write.writeArithmetic(parser.token)
                        break
                    case "eq":
                        write.writeArithmetic(parser.token)
                        break
                    case "gt":
                        write.writeArithmetic(parser.token)
                        break
                    case"lt":
                        write.writeArithmetic(parser.token)
                        break
                    case "and":
                        write.writeArithmetic(parser.token)
                        break
                    case"or":
                        write.writeArithmetic(parser.token)
                        break
                    case "not":
                        write.writeArithmetic(parser.token)
                        break
                }
                parser.advance()
            }
            write.saveCode()
            i++
        }
    }
}
