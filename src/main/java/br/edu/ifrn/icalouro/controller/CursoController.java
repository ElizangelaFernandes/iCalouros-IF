package br.edu.ifrn.icalouro.controller;

import org.springframework.http.MediaType;
import java.io.IOException;
import java.util.List;
import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifrn.icalouro.dominio.Arquivo;
import br.edu.ifrn.icalouro.dominio.Curso;
import br.edu.ifrn.icalouro.dominio.Materia;
import br.edu.ifrn.icalouro.repository.ArquivoRepository;
import br.edu.ifrn.icalouro.repository.CursoRepository;
import br.edu.ifrn.icalouro.repository.MateriaRepository;

/** Esta classe contém os métodos para o CRUD dos cursos. */
@Controller
@RequestMapping("/cursos")   
public class CursoController {
	@Autowired
	CursoRepository cursoRepository;
	
	@Autowired
	MateriaRepository materiaRepository;
	
	@Autowired
	ArquivoRepository arquivoRepository;
	
	@GetMapping("/")
	public String exibir(ModelMap model) {
		List<Curso> cursos = cursoRepository.findAll();
		model.addAttribute("cursos", cursos);
		return "cursos/exibir";
	}
	
	@GetMapping("/adicionar")
	public String adicionar(ModelMap model) {
	    model.addAttribute("curso", new Curso());
	    return "cursos/adicionar";
	}
	
	@PostMapping("/salvar")
	@Transactional(readOnly = false)
	public String salvar(@Valid Curso curso, BindingResult result, RedirectAttributes attr, @RequestParam("file") MultipartFile arquivo) {  
		if (result.hasErrors()) {
			  return "cursos/adicionar";
		}	      

	try {
	    if (arquivo != null && !arquivo.isEmpty()) {

	        String nomeArquivo = StringUtils.cleanPath(arquivo.getOriginalFilename());

	        Arquivo arquivoBD = new Arquivo(null, nomeArquivo, arquivo.getContentType(), arquivo.getBytes());

	        arquivoRepository.save(arquivoBD);

	        if (curso.getLogoCurso() != null && curso.getLogoCurso().getId() != null && curso.getLogoCurso().getId() > 0) {
	          arquivoRepository.delete(curso.getLogoCurso());
	        }

	        curso.setLogoCurso(arquivoBD);

	      } else {
	    	  curso.setLogoCurso(null);
	      }
		
		
	  	cursoRepository.save(curso);
	    attr.addFlashAttribute("msgSucesso", "A operação foi realizada com sucesso!");
	    
	} catch (IOException e) {
		 e.printStackTrace();
	}
	    
	    return "redirect:/cursos/";
	}
	
	@GetMapping("/buscar")
	public String buscar(@RequestParam(name = "nome", required = false) String nome, ModelMap model) {
		model.addAttribute("cursos", cursoRepository.findByNome(nome));
		return "cursos/exibir";
	}
		
	@GetMapping("/editar/{id}")
	public String editar(@PathVariable Integer id, ModelMap model) {
		model.addAttribute("curso", cursoRepository.findById(id).get());
		return "cursos/editar";
	}
	
	@GetMapping("/remover/{id}")
	public String remover(@PathVariable Integer id, RedirectAttributes attr) {	
		// Remover as matérias do curso antes de removê-lo para evitar problemas.
		List<Materia> materias = materiaRepository.findByidCurso(id);
		for (Materia m : materias) {
			materiaRepository.delete(m);
		}
		cursoRepository.deleteById(id);
		attr.addFlashAttribute("msgSucesso", "O curso foi removido com sucesso!");
	    return "redirect:/cursos/";
	}
	
	  @GetMapping("/gerarRelacaoCursos")
	  public ResponseEntity <?> gerarRelacaoCursos(@PathParam("salvar") String salvar) {
		  
		  List <Curso> lista = cursoRepository.findAll();
		  String conteudo = "RELAÇÃO DE CURSOS DISPONÍVEIS NO IFRN";

		    for (int i = 0; i < lista.size(); i++) {
		    	conteudo += "\n" + (i + 1) + " - " + lista.get(i).getNome();	
		    }
		    
		   
		   
		    Arquivo relacaoCursos = new Arquivo(null, "relacaoCursos.txt", "text/plain", conteudo.getBytes());
		  
		    String texto = (salvar == null || salvar.equals("true")) ? "attachment; filename=\"" + relacaoCursos.getNomeArquivo() + "\"" :
		      "inline; filename=\"" + relacaoCursos.getNomeArquivo()+ "\"";
		  
		  
		  
		    return ResponseEntity.ok()
		    	      .contentType(MediaType.parseMediaType(relacaoCursos.getTipoArquivo()))
		    	      .header(HttpHeaders.CONTENT_DISPOSITION, texto)
		    	      .body(new ByteArrayResource(relacaoCursos.getDados()));
		    
	  }
	
}
