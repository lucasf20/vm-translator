class Translator {
    static fileName = []
    static cods = []
    static outputFileName = ""

    static void main(String[] args){
        def dh = new File(args[0])
        def vmfiles = []
        try {
            dh.eachFile {
                if (it.toString().endsWith(".vm")) {
                    vmfiles.addAll(new String(it.getBytes()))
                    fileName.add(it.toString())
                }
            }
            for (def s : vmfiles) {
                cods.add(s)
            }
        }catch (Exception e){
            cods.add(new String(dh.getBytes()))
            //p.add(new Parser(new String(dh.getBytes())))
            fileName.add(dh.toString())
        }
        resolveOutputFile(args[0])
        getComands()
    }

    static void resolveOutputFile(String path){
        String[] aux = path.split("/")
        if(path.endsWith(".vm")){
            String optPath = "/"
            for(int i = 0; i < aux.length -1; i++){
                optPath += aux[i] + "/"
            }
            optPath += aux[aux.length - 2]
            outputFileName = optPath
        }else{
            outputFileName = path + "/" +aux[aux.length - 1]
        }
    }

    static void getComands(){
        int i = 0
        def tks = []
        for(c in cods){
            Parser p = new Parser(c)
            tks.add(p.tokens)
        }
        CodeWriter write = new CodeWriter()
        if(cods.size() > 1){
            write.writeInit()
        }
        else{
            String f = fileName[0]
            if(f.contains("Sys.vm")){
                write.writeInit()
            }
        }
        for(vm in tks){
            write.setFileName(fileName[i])
            i++
            for(int a = 0; a<vm.size(); a++){
                switch (vm[a]){
                    case "return":
                        write.writeReturn()
                        break
                    case "label":
                        a++
                        write.writeLabel(vm[a])
                        break
                    case "if-goto":
                        a++
                        write.writeIf(vm[a])
                        break
                    case "push":
                        a++
                        String segment = vm[a]
                        a++
                        write.writePush(segment,vm[a])
                        break
                    case "pop":
                        a++
                        String segment = vm[a]
                        a++
                        write.writePop(segment,vm[a])
                        break
                    case "function":
                        a++
                        String name = vm[a]
                        a++
                        write.writeFunction(name,vm[a])
                        break
                    case "call":
                        a++
                        String name = vm[a]
                        a++
                        write.writeCall(name,vm[a])
                        break
                    case "goto":
                        a++
                        write.writeGoto(vm[a])
                        break
                    case "add":
                        write.writeArithmetic(vm[a])
                        break
                    case"sub":
                        write.writeArithmetic(vm[a])
                        break
                    case"neg":
                        write.writeArithmetic(vm[a])
                        break
                    case "eq":
                        write.writeArithmetic(vm[a])
                        break
                    case "gt":
                        write.writeArithmetic(vm[a])
                        break
                    case"lt":
                        write.writeArithmetic(vm[a])
                        break
                    case "and":
                        write.writeArithmetic(vm[a])
                        break
                    case"or":
                        write.writeArithmetic(vm[a])
                        break
                    case "not":
                        write.writeArithmetic(vm[a])
                        break
                }
            }
        }
        write.saveCode(outputFileName)
    }
}
