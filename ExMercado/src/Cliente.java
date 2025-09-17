public class Cliente {
    String nome;
    String cpf;

    public Cliente(String nome, String cpf) {
        this.nome = nome;
        this.cpf = cpf;
    }

    public String getNome() {
        return this.nome;
    }

    public String getCpf() {
        return this.cpf;
    }
}

//public static void main(String[] args) {
//    Cliente c = new Cliente("silvasauro da silva", "012.345.678-96");
//    System.out.println(c.nome);
//    System.out.println(c.cpf);
//    System.out.println(c.getCpf());
//}
