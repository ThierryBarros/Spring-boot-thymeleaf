package com.example.demo.analise;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class AnaliseController {
	
	@Autowired
	private AnaliseService service;

	@PostMapping("/analises")
	public String cadastrarAnalise(@RequestBody Analise analise) {
		service.save(analise);
		return "redirect:/";
	}
	
	
	@GetMapping("/")
	public String listarAnalises(Model model,@RequestParam(defaultValue = "0") int page) {
		if(page<0) {
			page=0;
		}
		model.addAttribute("data",service.listarAnalises(page));
		model.addAttribute("currentPage",page);
		return "lista";
	}
	
	 @GetMapping(value = "/downloadExcel")
	    public String download(HttpServletResponse response,@RequestParam("codigo") String codigo) throws IOException {
	    	
	    	Workbook wb = null;
	        try {
	        	System.out.println(service.listarPorCodigo(codigo).size());
	            wb = service.createWorkbook(service.listarPorCodigo(codigo));
	            
	            response.setContentType("application/vnd.ms-excel"); 
	            response.setHeader("Content-Disposition", "attachment; filename=\""+codigo+".xlsx\"");
	            wb.write(response.getOutputStream());
	        } catch (IOException ioe) {
	            throw new RuntimeException("Error writing spreadsheet to output stream");
	        } finally {
	            if (wb != null) {
	                wb.close();
	            }
	            
	        }
	        return "redirect:/";
	    }
	 
	 @GetMapping(value = "/buscarAnalise")
	 public String buscar() {
		 return "buscarAnalise";
	   }
	
}

