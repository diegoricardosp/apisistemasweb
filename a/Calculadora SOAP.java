@WebService

@SOAPBinding(style = Style.RPC)

public class Calculadora {

    @WebMethod(action = "somar")
    public double somar (
        @WebParam(name = "num1") double num1,
        @WebParam(name = "num2") double num2) {
            return num1 + num2;
        }
    
}
