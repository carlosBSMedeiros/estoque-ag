package br.com.estoqueag.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
public class Produto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;
	
	private String descricao;
	
    @Enumerated(EnumType.STRING)
	private ProdutoSituacao situacao;
    private Date dataFabricacao;
    private Date dataValidade;
    private String codigoFornecedor;
    private String descricaoFornecedor;
    private String cnpjFornecedor;
	
}
