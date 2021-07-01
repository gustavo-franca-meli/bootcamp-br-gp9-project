# US0001 - COMO Representante do armazém, QUERO inserir um lote de produtos PARA registrar a existência do estoque.
*Mermaid funciona apenas com plugins do github e do fury para ver acesse [meramaid](https://mermaid-js.github.io/mermaid-live-editor/) e cole 
````mermaid
graph TD
    A[Requisição] --> | Sim | B{ Representante está logado?}
    B -->| Sim | C{ Armazem é válido?}
    C --> | Sim | D{ Representante Pertence Ao armazem?}
    D --> | Sim | E{Setor é valido?}
    E --> | Sim | F(Iterar Lista de ProdutosBath)
    F --> | SIm | H{Produto do Seller está registrado?}
    H --> | Sim | I{Setor Corresponde Ao tipo de Produto?}
    I --> | Sim | J{Setor tem Espaço?}
    J --> | Sim | K{ Tem mais ProdutosBath?}
    K --> | Sim | F
    K --> | Não | L(Registrar Lote em Setor)
    L --> M(Registrar Compra e associar o registro de Compra)
    M --> N( Retornar Os ProdutosBath Registrados )
````
