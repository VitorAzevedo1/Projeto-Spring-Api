package br.com.projeto.api.servico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.projeto.api.controle.repositorio.Repositorio;
import br.com.projeto.api.modelo.Mensagem;
import br.com.projeto.api.modelo.Pessoa;

@Service
public class Servico {
    
    @Autowired
    private Mensagem mensagem;

    @Autowired
    private Repositorio acao;

    // Metodo para cadastrar pessoas
    public ResponseEntity<?> cadastrar(Pessoa obj){

        if(obj.getNome().equals("")){
            mensagem.setMensagem("O nome precisa ser preesnchido");
            return new ResponseEntity<>(mensagem, HttpStatus.BAD_REQUEST);
        }
        else if(obj.getIdade() < 0){
            mensagem.setMensagem("Informe uma idade valida");
            return new ResponseEntity<>(mensagem, HttpStatus.BAD_REQUEST);
        }
        else{
            return new ResponseEntity<>(acao.save(obj), HttpStatus.CREATED);
        }
    }

    // Metodo para selecionar pessoas
    public ResponseEntity<?> selecionar(){
        return new ResponseEntity<>(acao.findAll(), HttpStatus.OK);
    }

    // Metodo para selecionar pessoas atraves do codigo
    public ResponseEntity<?> selecionarPeloCodigo(int codigo){
        
        if(acao.countByCodigo(codigo) == 0){
            mensagem.setMensagem("Não foi encontrada nenhuma pessoa.");
            return new ResponseEntity<>(mensagem, HttpStatus.BAD_REQUEST);
        }
        else{
            return new ResponseEntity<>(acao.findByCodigo(codigo), HttpStatus.OK);
        }
    }

    //Metodo para editar dados
    public ResponseEntity<?> editar(Pessoa obj){
        if (acao.countByCodigo(obj.getCodigo()) == 0){
            mensagem.setMensagem("O código informado não existe.");
            return new ResponseEntity<>(mensagem, HttpStatus.NOT_FOUND);
        }
        else if(obj.getNome().equals("")){
            mensagem.setMensagem("É necessário informar um nome");
            return new ResponseEntity<>(mensagem, HttpStatus.BAD_REQUEST);
        }
        else if(obj.getIdade() < 0){
            mensagem.setMensagem("Informe uma idade valida");
            return new ResponseEntity<>(mensagem, HttpStatus.BAD_REQUEST);
        }
        else{
            return new ResponseEntity<>(acao.save(obj), HttpStatus.OK);
        }
    }

    //Metodo para remover registros
    public ResponseEntity<?> remover(int codigo){
        if(acao.countByCodigo(codigo) == 0){
            mensagem.setMensagem("O código informado não existe");
            return new ResponseEntity<>(mensagem, HttpStatus.NOT_FOUND);
        }
        else{
            Pessoa obj = acao.findByCodigo(codigo);
            acao.delete(obj);

            mensagem.setMensagem("Pessoa removida com sucesso!");
            return new ResponseEntity<>(mensagem, HttpStatus.OK);
        }
    }
}
