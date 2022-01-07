package br.edu.ifrn.icalouro.controller;


import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import br.edu.ifrn.icalouro.dominio.Curso;
import br.edu.ifrn.icalouro.dominio.Materia;
import br.edu.ifrn.icalouro.repository.CursoRepository;
import br.edu.ifrn.icalouro.repository.MateriaRepository;

/** Esta classe contém os métodos para o CRUD das matérias. */
@Controller
@RequestMapping("/cursos/materias")
public class MateriaController {
	
	@Autowired
	MateriaRepository materiaRepository;	
	
	@Autowired
	CursoRepository cursoRepository;

	@GetMapping("/{id}")
	public String exibir(@PathVariable("id") Integer id, ModelMap model) {
		model.addAttribute("materias", materiaRepository.findByidCurso(id));
		model.addAttribute("curso", cursoRepository.findById(id).get());
		return "materias/exibir";
	}
	
	@GetMapping("/adicionar")
	public String adicionar(@RequestParam(name = "id", required = false) Integer id, ModelMap model) {
		Curso curso = cursoRepository.findById(id).get();
		Materia materia = new Materia();;
	    materia.setCurso(curso);
	    model.addAttribute("materia", materia);
	    return "materias/adicionar";
	}
	
	@PostMapping("/salvar")
	@Transactional(readOnly = false)
	public String salvar(@Valid Materia materia, BindingResult result, RedirectAttributes attr) {  	
		if (result.hasErrors()) {
			  return "materias/adicionar";
		  }
		materiaRepository.save(materia);
	    attr.addFlashAttribute("msgSucesso", "A operação foi realizada com sucesso!");
	    return "redirect:/cursos/materias/" +  materia.getCurso().getId();
	}
	
	@GetMapping("/editar/{id}")
	public String editar(@PathVariable Integer id, ModelMap model) {
		model.addAttribute("materia", materiaRepository.findById(id).get());
		return "materias/editar";
	}
	
	@GetMapping("/buscar")
	public String buscar(@RequestParam(name = "nome", required = false) String nome, @RequestParam(name = "id", required = false) Integer id,
			ModelMap model) {
		List<Materia> materiasDoCurso = materiaRepository.findByidCurso(id);	
		List<Materia> resultado = new ArrayList<>();
		for	(Materia materia : materiasDoCurso) {
				if (materia.getNome().contains(nome)) {
					resultado.add(materia);
				}
		}
		model.addAttribute("materias", resultado);
		model.addAttribute("curso", cursoRepository.findById(id).get());
		return "materias/exibir";
	}

	@GetMapping("/remover/{id}")
	public String remover(@PathVariable Integer id, RedirectAttributes attr) {
		Materia materia = materiaRepository.findById(id).get();
		materiaRepository.deleteById(id);
		attr.addFlashAttribute("msgSucesso", "A matéria foi removida com sucesso!");
	    return "redirect:/cursos/materias/" +  materia.getCurso().getId();
	}

}
