package br.com.estoqueag.dtos;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import br.com.estoqueag.entities.enums.ProdutoSituacao;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="produto")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ProdutoDTO extends DTOGenerico{
	private static final long serialVersionUID = 1L;
	
	private Long codigo;
	private String descricao;
	private ProdutoSituacao situacao;
    private Date dataFabricacao;
    private Date dataValidade;
    private String codigoFornecedor;
    private String descricaoFornecedor;
    private String cnpjFornecedor;
	
}
