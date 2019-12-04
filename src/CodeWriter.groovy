class CodeWriter {
    String code = ""
    String moduleName, moduleName2
    int labelCount = 0
    int callCount = 0
    int returnSubCount
    String funcName = ""

    CodeWriter(String path){
        setFileName(path)
        code = ""
        labelCount = 0
        returnSubCount = 0
        funcName = ""
    }

    void write(String command){
        code += command + "\n"
    }

    String segmentPointer(String segment, String index){
        int ind = Integer.parseInt(index)
        switch (segment){
            case "local":
                return "LCL"
            case "argument":
                return "ARG"
            case "this":
                return segment.toUpperCase()
            case "that":
                return segment.toUpperCase()
            case "temp":
                return "R" + (5 + ind)
            case "pointer":
                return "R" + (3 + ind)
            case "static":
                return funcName + "." + index
            default:
                return "ERROR"
        }
    }
//    void writeInit(){
//        write("@256")
//        write("D=A")
//        write("@SP")
//        write("M=D")
//        writeCall("Sys.init", "0")
//        writeSubroutineReturn()
//        writeSubArithmeticLt()
//        writeSubArithmeticGt()
//        writeSubArithmeticEq()
//    }

//    void  writeSubroutineReturn(){
//        write("(\$RETURN\$)")
//        write("@R13")
//        write("M=D")
//
//        write("@LCL")
//        write("D=M")
//
//        write("@R13")
//        write("M=D")
//
//        write("@5")
//        write("A=D-A")
//        write("D=M")
//        write("@R14")
//        write("M=D")
//
//        write("@SP")
//        write("AM=M-1")
//        write("D=M")
//        write("@ARG")
//        write("A=M")
//        write("M=D")
//
//        write("D=A")
//        write("@SP")
//        write("M=D+1")
//
//        write("@R13")
//        write("AM=M-1")
//        write("D=M")
//        write("@THAT")
//        write("M=D")
//
//        write("@R13")
//        write("AM=M-1")
//        write("D=M")
//        write("@THIS")
//        write("M=D")
//
//        write("@R13")
//        write("AM=M-1")
//        write("D=M")
//        write("@ARG")
//        write("M=D")
//
//        write("@R13")
//        write("AM=M-1")
//        write("D=M")
//        write("@LCL")
//        write("M=D")
//
//        write("@R14")
//        write("A=M")
//        write("0;JMP")
//
//        write("@R13")
//        write("0;JMP")
//    }
//
//    void writeSubArithmeticGt(){
//        write("(\$GT\$)")
//        write("@R13")
//        write("M=D")
//
//        String labelTrue, labelFalse
//
//        labelTrue = "JGT_TRUE_" + moduleName + "_" + labelCount
//        labelFalse = "JGT_FALSE_" + moduleName + "_" + labelCount
//
//        write("@SP // gt")
//        write("AM=M-1")
//        write("D=M")
//        write("@SP")
//        write("AM=M-1")
//        write("D=M-D")
//        write("@" + labelTrue)
//        write("D;JGT")
//        write("D=0")
//        write("@" + labelFalse)
//        write("0;JMP")
//        write("(" + labelTrue + ")")
//        write("D=-1")
//        write("(" + labelFalse + ")")
//        write("@SP")
//        write("A=M")
//        write("M=D")
//        write("@SP")
//        write("M=M+1")
//
//        labelCount++
//
//        write("@R13")
//        write("0;JMP")
//    }
//
//    void writeSubArithmeticEq(){
//        write("(\$EQ\$)")
//        write("@R13")
//        write("M=D")
//
//        String label  =  "JEQ_" + moduleName + "_" + labelCount
//
//        write("@SP // eq")
//        write("AM=M-1")
//        write("D=M")
//        write("@SP")
//        write("AM=M-1")
//        write("D=M-D")
//        write("@" + label)
//        write("D;JEQ")
//        write("D=1")
//        write("(" + label + ")")
//        write("D=D-1")
//        write("@SP")
//        write("A=M")
//        write("M=D")
//        write("@SP")
//        write("M=M+1")
//
//        labelCount++
//
//        write("@R13")
//        write("0;JMP")
//    }
//
//    void writeSubArithmeticLt(){
//        write("(\$LT\$)")
//        write("@R13")
//        write("M=D")
//
//        String labelTrue = "JLT_TRUE_"+ moduleName +"_"+labelCount
//        String labelFalse = "JLT_FALSE_"+ moduleName +"_"+labelCount
//
//        write("@SP // lt")
//        write("AM=M-1")
//        write("D=M")
//        write("@SP")
//        write("AM=M-1")
//        write("D=M-D")
//        write("@" + labelTrue + "")
//        write("D;JLT")
//        write("D=0")
//        write("@" + labelFalse + "")
//        write("0;JMP")
//        write("(" + labelTrue + ")")
//        write("D=-1")
//        write("(" + labelFalse + ")")
//        write("@SP")
//        write("A=M")
//        write("M=D")
//        write("@SP")
//        write("M=M+1")
//
//        labelCount++
//
//        write("@R13")
//        write("0;JMP")
//    }

    void setFileName(String path){
        moduleName2 = path.replace(".vm","")
        String [] module = moduleName2.split("/")
        moduleName = module[module.length - 1]
    }

    void writePush(String seg, String index) {
        switch (seg) {
            case "constant":
                write("@"+index+" // push "+ seg +" " + index)
                write("D=A")
                write("@SP")
                write("A=M")
                write("M=D")
                write("@SP")
                write("M=M+1")
                break
            case "static":
                write("@" + segmentPointer(seg,index) + " //push " + seg + " " + index)
                write("D=M")
                write("@SP")
                write("A=M")
                write("M=D")
                write("@SP")
                write("M=M+1")
                break
            case "temp":
                write("@" + segmentPointer(seg,index) + " //push " + seg + " " + index)
                write("D=M")
                write("@SP")
                write("A=M")
                write("M=D")
                write("@SP")
                write("M=M+1")
                break
            case "pointer":
                write("@" + segmentPointer(seg,index) + " //push " + seg + " " + index)
                write("D=M")
                write("@SP")
                write("A=M")
                write("M=D")
                write("@SP")
                write("M=M+1")
                break
            case "local":
                write("@" + segmentPointer(seg,index) + " //push " + seg + " " + index)
                write("D=M")
                write("@"+index)
                write("A=D+A")
                write("D=M")
                write("@SP")
                write("A=M")
                write("M=D")
                write("@SP")
                write("M=M+1")
                break
            case "argument":
                write("@" + segmentPointer(seg,index) + " //push " + seg + " " + index)
                write("D=M")
                write("@"+index)
                write("A=D+A")
                write("D=M")
                write("@SP")
                write("A=M")
                write("M=D")
                write("@SP")
                write("M=M+1")
                break
            case "this":
                write("@" + segmentPointer(seg,index) + " //push " + seg + " " + index)
                write("D=M")
                write("@"+index)
                write("A=D+A")
                write("D=M")
                write("@SP")
                write("A=M")
                write("M=D")
                write("@SP")
                write("M=M+1")
                break
            case "that":
                write("@" + segmentPointer(seg,index) + " //push " + seg + " " + index)
                write("D=M")
                write("@"+index)
                write("A=D+A")
                write("D=M")
                write("@SP")
                write("A=M")
                write("M=D")
                write("@SP")
                write("M=M+1")
                break
        }
    }

    void writePop(String seg, String index) {
        switch (seg) {
            case "static":
                write("@SP // pop " + seg + " " + index)
                write("M=M-1")
                write("A=M")
                write("D=M")
                write("@"+segmentPointer(seg,index))
                write("M=D")
                break
            case"temp":
                write("@SP // pop " + seg + " " + index)
                write("M=M-1")
                write("A=M")
                write("D=M")
                write("@"+segmentPointer(seg,index))
                write("M=D")
                break
            case"pointer":
                write("@SP // pop " + seg + " " + index)
                write("M=M-1")
                write("A=M")
                write("D=M")
                write("@"+segmentPointer(seg,index))
                write("M=D")
                break
            case "local":
                write("@"+ segmentPointer(seg, index) +"// pop " + seg +" "+ index)
                write("D=M")
                write("@"+index)
                write("D=D+A")
                write("@R13")
                write("M=D")
                write("@SP")
                write("M=M-1")
                write("A=M")
                write("D=M")
                write("@R13")
                write("A=M")
                write("M=D")
                break
            case"argument":
                write("@"+ segmentPointer(seg, index) +"// pop " + seg +" "+ index)
                write("D=M")
                write("@"+index)
                write("D=D+A")
                write("@R13")
                write("M=D")
                write("@SP")
                write("M=M-1")
                write("A=M")
                write("D=M")
                write("@R13")
                write("A=M")
                write("M=D")
                break
            case"this":
                write("@"+ segmentPointer(seg, index) +"// pop " + seg +" "+ index)
                write("D=M")
                write("@"+index)
                write("D=D+A")
                write("@R13")
                write("M=D")
                write("@SP")
                write("M=M-1")
                write("A=M")
                write("D=M")
                write("@R13")
                write("A=M")
                write("M=D")
                break
            case"that":
                write("@"+ segmentPointer(seg, index) +"// pop " + seg +" "+ index)
                write("D=M")
                write("@"+index)
                write("D=D+A")
                write("@R13")
                write("M=D")
                write("@SP")
                write("M=M-1")
                write("A=M")
                write("D=M")
                write("@R13")
                write("A=M")
                write("M=D")
                break
        }

    }

    void writeArithmetic(String command) {
        switch (command) {
            case "add":
                writeArithmeticAdd()
                break
            case "sub":
                writeArithmeticSub()
                break
            case "neg":
                writeArithmeticNeg()
                break
            case "eq":
                writeArithmeticEq()
                break
            case "gt":
                writeArithmeticGt()
                break
            case "lt":
                writeArithmeticLt()
                break
            case "and":
                writeArithmeticAnd()
                break
            case "or":
                writeArithmeticOr()
                break
            case "not":
                writeArithmeticNot()
                break
        }
    }

    void writeBinaryArithmetic() {
        write("@SP")
        write("AM=M-1")
        write("D=M")
        write("A=A-1")
    }

    void writeArithmeticAdd() {
        writeBinaryArithmetic()
        write("M=D+M")
    }

    void writeArithmeticSub() {
        writeBinaryArithmetic()
        write("M=M-D")
    }

    void writeArithmeticAnd() {
        writeBinaryArithmetic()
        write("M=D&M")
    }

    void writeArithmeticOr() {
        writeBinaryArithmetic()
        write("M=D|M")
    }

    void writeUnaryArithmetic() {
        write("@SP")
        write("A=M")
        write("A=A-1")
    }

    void writeArithmeticNeg() {
        writeUnaryArithmetic()
        write("M=-M")
    }

    void writeArithmeticNot() {
        writeUnaryArithmetic()
        write("M=!M")
    }

    void writeArithmeticEq() {
        String label = "JEQ_" + moduleName + "_" + labelCount
        write("@SP // eq")
        write("AM=M-1")
        write("D=M")
        write("@SP")
        write("AM=M-1")
        write("D=M-D")
        write("@" + label)
        write("D;JEQ")
        write("D=1")
        write("(" + label + ")")
        write("D=D-1")
        write("@SP")
        write("A=M")
        write("M=D")
        write("@SP")
        write("M=M+1")
        labelCount++
    }

    void writeArithmeticGt() {
        String labelTrue = "JGT_TRUE_" + moduleName +"_" +labelCount
        String labelFalse = "JGT_FALSE_" + moduleName +"_" +labelCount

        write("@SP // gt")
        write("AM=M-1")
        write("D=M")
        write("@SP")
        write("AM=M-1")
        write("D=M-D")
        write("@" + labelTrue)
        write("D;JGT")
        write("D=0")
        write("@" + labelFalse)
        write("0;JMP")
        write("(" + labelTrue + ")")
        write("D=-1")
        write("(" + labelFalse + ")")
        write("@SP")
        write("A=M")
        write("M=D")
        write("@SP")
        write("M=M+1")

        labelCount++

    }

    void writeArithmeticLt() {
        String labelTrue = "JGT_TRUE_" + moduleName +"_" +labelCount
        String labelFalse = "JGT_FALSE_" + moduleName +"_" +labelCount
        write("@SP // lt")
        write("AM=M-1")
        write("D=M")
        write("@SP")
        write("AM=M-1")
        write("D=M-D")
        write("@" + labelTrue)
        write("D;JLT")
        write("D=0")
        write("@" + labelFalse)
        write("0;JMP")
        write("(" + labelTrue + ")")
        write("D=-1")
        write("(" + labelFalse + ")")
        write("@SP")
        write("A=M")
        write("M=D")
        write("@SP")
        write("M=M+1")

        labelCount++

    }

//    void writeLabel(String label ) {
//        write("(" + label + ")")
//    }
//
//    void writeGoto(String label) {
//        write("@" + label)
//        write("0;JMP")
//    }
//
//    void writeIf(String label) {
//        write("@SP")
//        write("AM=M-1")
//        write("D=M")
//        write("M=0")
//        write("@" + label)
//        write("D;JNE")
//
//    }
//
//    void writeFunction(String funcName, String nLocals) {
//
//        String loopLabel = funcName + "_INIT_LOCALS_LOOP"
//        String loopEndLabel = funcName + "_INIT_LOCALS_END"
//
//        this.funcName = funcName
//
//        write("(" + funcName + ")" + "// initialize local variables")
//        write("@"+ nLocals)
//        write("D=A")
//        write("@R13") // temp
//        write("M=D")
//        write("(" + loopLabel + ")")
//        write("@" + loopEndLabel)
//        write("D;JEQ")
//        write("@0")
//        write("D=A")
//        write("@SP")
//        write("A=M")
//        write("M=D")
//        write("@SP")
//        write("M=M+1")
//        write("@R13")
//        write("MD=M-1")
//        write("@" + loopLabel)
//        write("0;JMP")
//        write("(" + loopEndLabel + ")")
//
//    }
//
//    void writeFramePush(String value) {
//        write("@" + value)
//        write("D=M")
//        write("@SP")
//        write("A=M")
//        write("M=D")
//        write("@SP")
//        write("M=M+1")
//    }
//
//    void writeCall(String funcName, String numArgs) {
//
//        String comment = "// call " + funcName +" "+ numArgs
//
//        String returnAddr = funcName + "_RETURN_"+ callCount
//        callCount++
//
//        write("@"+ returnAddr +" "+ comment)
//        write("D=A")
//        write("@SP")
//        write("A=M")
//        write("M=D")
//        write("@SP")
//        write("M=M+1")
//
//        writeFramePush("LCL")
//        writeFramePush("ARG")
//        writeFramePush("THIS")
//        writeFramePush("THAT")
//
//        write("@"+ numArgs)
//        write("D=A")
//        write("@5")
//        write("D=D+A")
//        write("@SP")
//        write("D=M-D")
//        write("@ARG")
//        write("M=D")
//
//        write("@SP")
//        write("D=M")
//        write("@LCL")
//        write("M=D")
//
//        writeGoto(funcName)
//
//        write("(" + returnAddr + ")") // (return-address)
//
//    }
//
//    void writeReturn() {
//        String returnAddr = "\$RET" + returnSubCount
//        write("@"+ returnAddr)
//        write("D=A")
//        write("@\$RETURN\$")
//        write("0;JMP")
//        write("("+ returnAddr + ")")
//        returnSubCount++
//
//    }
    void saveCode(){
        println("Saving file " + moduleName2 + ".asm")
        File file = new File(moduleName2 + ".asm")
        file.write(code)
    }

}
