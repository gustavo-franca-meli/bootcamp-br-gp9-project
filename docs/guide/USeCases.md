# US0001 - COMO Representante do armazém, QUERO inserir um lote de produtos PARA registrar a existência do estoque.

### User Story Code: ml-insert-batch-in-fulfillment-warehouse-01

### User Story Name: Insira o lote no armazém de distruibuição

#### Horas estimadas 28h

COMO Representante do armazém, 

QUERO inserir um lote de produtos

PARA registrar a existência do estoque. 

```text
CENÁRIO 1: produto de um Seller está registrado.
DADO QUE o produto de um Sellers está registrado
E que o armazém é válido
E que o representante pertence ao armazém
E que o setor é válido
E que o setor corresponde ao tipo de produto
E que o setor tenha espaço disponível
QUANDO o representante entra no lote
ENTÃO o registro de compra é criado
E o lote é atribuído a um setor
E o representante está associado ao registro de compra
VALIDAÇÃO
    ▪ Autentique-se como representante e acesse os endpoints.
    ▪ Cadastrar lote no setor correspondente.
    ▪ Verifique se o setor do armazém está sendo registrado corretamente.
```

Routes 


| HTTP | URI   |Description |
| ----------- | ---- | --------- |
| POST | /api/v1/fresh-products/inboundorder/ |  Cadastre um lote com o estoque de produtos que o compõe. Retorne o novo lote com o código de status “201 CREATED”. |
| PUT | /api/v1/fresh-products/inboundorder/ |so o lote já exista e deva ser atualizado. Retorne o estoque atualizado com o código de status “201 CREATED”. |

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
[![](https://mermaid.ink/img/eyJjb2RlIjoiZ3JhcGggVERcbiAgICBBW1JlcXVpc2nDp8Ojb10gLS0-IHwgU2ltIHwgQnsgUmVwcmVzZW50YW50ZSBlc3TDoSBsb2dhZG8_fVxuICAgIEIgLS0-fCBTaW0gfCBDeyBBcm1hemVtIMOpIHbDoWxpZG8_fVxuICAgIEMgLS0-IHwgU2ltIHwgRHsgUmVwcmVzZW50YW50ZSBQZXJ0ZW5jZSBBbyBhcm1hemVtP31cbiAgICBEIC0tPiB8IFNpbSB8IEV7U2V0b3Igw6kgdmFsaWRvP31cbiAgICBFIC0tPiB8IFNpbSB8IEYoSXRlcmFyIExpc3RhIGRlIFByb2R1dG9zQmF0aClcbiAgICBGIC0tPiB8IFNJbSB8IEh7UHJvZHV0byBkbyBTZWxsZXIgZXN0w6EgcmVnaXN0cmFkbz99XG4gICAgSCAtLT4gfCBTaW0gfCBJe1NldG9yIENvcnJlc3BvbmRlIEFvIHRpcG8gZGUgUHJvZHV0bz99XG4gICAgSSAtLT4gfCBTaW0gfCBKe1NldG9yIHRlbSBFc3Bhw6dvP31cbiAgICBKIC0tPiB8IFNpbSB8IEt7IFRlbSBtYWlzIFByb2R1dG9zQmF0aD99XG4gICAgSyAtLT4gfCBTaW0gfCBGXG4gICAgSyAtLT4gfCBOw6NvIHwgTChSZWdpc3RyYXIgTG90ZSBlbSBTZXRvcilcbiAgICBMIC0tPiBNKFJlZ2lzdHJhciBDb21wcmEgZSBhc3NvY2lhciBvIHJlZ2lzdHJvIGRlIENvbXByYSlcbiAgICBNIC0tPiBOKCBSZXRvcm5hciBPcyBQcm9kdXRvc0JhdGggUmVnaXN0cmFkb3MgKVxuXG4gICAgXG4gICAgXG4gICAgIiwibWVybWFpZCI6eyJ0aGVtZSI6ImRlZmF1bHQifSwidXBkYXRlRWRpdG9yIjpmYWxzZSwiYXV0b1N5bmMiOnRydWUsInVwZGF0ZURpYWdyYW0iOmZhbHNlfQ)](https://mermaid-js.github.io/mermaid-live-editor/edit##eyJjb2RlIjoiZ3JhcGggVERcbiAgICBBW1JlcXVpc2nDp8Ojb10gLS0-IHwgU2ltIHwgQnsgUmVwcmVzZW50YW50ZSBlc3TDoSBsb2dhZG8_fVxuICAgIEIgLS0-fCBTaW0gfCBDeyBBcm1hemVtIMOpIHbDoWxpZG8_fVxuICAgIEMgLS0-IHwgU2ltIHwgRHsgUmVwcmVzZW50YW50ZSBQZXJ0ZW5jZSBBbyBhcm1hemVtP31cbiAgICBEIC0tPiB8IFNpbSB8IEV7U2V0b3Igw6kgdmFsaWRvP31cbiAgICBFIC0tPiB8IFNpbSB8IEYoSXRlcmFyIExpc3RhIGRlIFByb2R1dG9zQmF0aClcbiAgICBGIC0tPiB8IFNJbSB8IEh7UHJvZHV0byBkbyBTZWxsZXIgZXN0w6EgcmVnaXN0cmFkbz99XG4gICAgSCAtLT4gfCBTaW0gfCBJe1NldG9yIENvcnJlc3BvbmRlIEFvIHRpcG8gZGUgUHJvZHV0bz99XG4gICAgSSAtLT4gfCBTaW0gfCBKe1NldG9yIHRlbSBFc3Bhw6dvP31cbiAgICBKIC0tPiB8IFNpbSB8IEt7IFRlbSBtYWlzIFByb2R1dG9zQmF0aD99XG4gICAgSyAtLT4gfCBTaW0gfCBGXG4gICAgSyAtLT4gfCBOw6NvIHwgTChSZWdpc3RyYXIgTG90ZSBlbSBTZXRvcilcbiAgICBMIC0tPiBNKFJlZ2lzdHJhciBDb21wcmEgZSBhc3NvY2lhciBvIHJlZ2lzdHJvIGRlIENvbXByYSlcbiAgICBNIC0tPiBNKCBSZXRvcm5hciBPcyBQcm9kdXRvc0JhdGggUmVnaXN0cmFkb3MgKVxuXG4gICAgXG4gICAgXG4gICAgIiwibWVybWFpZCI6IntcbiAgXCJ0aGVtZVwiOiBcImRlZmF1bHRcIlxufSIsInVwZGF0ZUVkaXRvciI6ZmFsc2UsImF1dG9TeW5jIjp0cnVlLCJ1cGRhdGVEaWFncmFtIjpmYWxzZX0)