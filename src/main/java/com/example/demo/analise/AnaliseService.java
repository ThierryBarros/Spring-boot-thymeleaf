package com.example.demo.analise;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
@Service
public class AnaliseService {
	
	@Autowired
	private AnaliseRepository repository;
	
	@Autowired
	private AnaliseSensorialRepository sensorial;
	

	public Analise save(Analise analise) {
		if(sensorial.findByCodigo(analise.getCodigoAnalise())==null) {
			AnaliseSensorial senso = new AnaliseSensorial(analise.getNomeproduto(),analise.getCodigoAnalise());
			sensorial.save(senso);
		}
		return repository.save(analise);
	}
	
	public Page<AnaliseSensorial> listarAnalises(int page) {
		return sensorial.findAll(PageRequest.of(page,4));
	}
	
	

	public List<Analise> listar() {
		
		return repository.findAll();
	}

	public List<Analise> listarPorCodigo(String codigo) {
		
		return repository.findByCodigoAnalise(codigo);
	}

	public Workbook createWorkbook(List<Analise> analises) throws IOException {
		
		
		
		Workbook workbook = new XSSFWorkbook();
		
		if(analises == null || analises.isEmpty()) {
			 Sheet sheet = workbook.createSheet("Nulo");
		     sheet.setDefaultColumnWidth(30);
			 return workbook;
	      }
        
		List<String> categorias = createCategoria(analises);
        List<String> lista = createLista(analises);
        ArrayList<Sheet> sheets = new ArrayList<>();
		int j = 0;   
        int i = 0;
        for(String atributo : lista){
        	sheets.add(workbook.createSheet(atributo));
            sheets.get(i).setDefaultColumnWidth(30);
            
            Row header = sheets.get(i++).createRow(0);
            
            j = 0;
            
            for(String categoria : categorias) {
            	header.createCell(j++).setCellValue(categoria);
            }
   
            
        }
        
        lista.add("Idade");
        lista.add("Genero");
        
	    sheets.add(workbook.createSheet("Idade"));
        
	    sheets.get(i).setDefaultColumnWidth(30);
        
        
        Row header = sheets.get(i++).createRow(0);
        header.createCell(0).setCellValue("Idade");
        
        sheets.add(workbook.createSheet("Genero"));
        sheets.get(i).setDefaultColumnWidth(30);
        
        header = sheets.get(i++).createRow(0);
        header.createCell(0).setCellValue("Genero");
		
       
	
       
        i = 0;  
          
        int posi= 0;
        

        for(Analise analise : analises){
        	if(sheets.get(0).getRow(analise.getPosicao()+1)==null) {
	        	for(Sheet sheet : sheets) {
	        		sheet.createRow(analise.getPosicao()+1);
	        	}

        	}
        	
        	if(analise.getCategoria().equals("A")) {
	        	posi = 0;
        	}
        	if(analise.getCategoria().equals("B")) {
	        	posi = 1;
        	}
        	if(analise.getCategoria().equals("C")) {
	        	posi = 2;
        	}
        	if(analise.getCategoria().equals("D")) {
	        	posi = 3;
        	}
        	if(analise.getCategoria().equals("E")) {
	        	posi = 4;
        	}
        	
        	i = 0;
        	sheets.get(i++).getRow(analise.getPosicao()+1).createCell(posi).setCellValue(analise.getCodigo());
        	if(lista.contains("Sabor")) {
        		sheets.get(i++).getRow(analise.getPosicao()+1).createCell(posi).setCellValue(analise.getSabor());
        	}
        	if(lista.contains("Aroma")) {
        	sheets.get(i++).getRow(analise.getPosicao()+1).createCell(posi).setCellValue(analise.getAroma());
        	}
        	if(lista.contains("Aparencia")) {
        	sheets.get(i++).getRow(analise.getPosicao()+1).createCell(posi).setCellValue(analise.getAparencia());
        	}
        	if(lista.contains("Sabor Residual")) {
        	sheets.get(i++).getRow(analise.getPosicao()+1).createCell(posi).setCellValue(analise.getSaborresidual());
        	}
        	if(lista.contains("Doçura")) {
        	sheets.get(i++).getRow(analise.getPosicao()+1).createCell(posi).setCellValue(analise.getDocura());
        	}
        	if(lista.contains("Textura")) {
        	sheets.get(i++).getRow(analise.getPosicao()+1).createCell(posi).setCellValue(analise.getTextura());
        	}
        	if(lista.contains("Odor")) {
        	sheets.get(i++).getRow(analise.getPosicao()+1).createCell(posi).setCellValue(analise.getOdor());
        	}
        	if(lista.contains("Maciez")) {
        	sheets.get(i++).getRow(analise.getPosicao()+1).createCell(posi).setCellValue(analise.getMaciez());
        	}
        	if(lista.contains("Cor")) {
        	sheets.get(i++).getRow(analise.getPosicao()+1).createCell(posi).setCellValue(analise.getCor());
        	}
        	if(lista.contains("Consistencia")) {
        	sheets.get(i++).getRow(analise.getPosicao()+1).createCell(posi).setCellValue(analise.getConsistencia());
        	}
        	if(lista.contains("Avaliação Global")) {
        	sheets.get(i++).getRow(analise.getPosicao()+1).createCell(posi).setCellValue(analise.getAvalglobal());
        	}
        	sheets.get(i++).getRow(analise.getPosicao()+1).createCell(posi).setCellValue(analise.getIntencao());
        	sheets.get(i++).getRow(analise.getPosicao()+1).createCell(0).setCellValue(analise.getIdade());
        	sheets.get(i++).getRow(analise.getPosicao()+1).createCell(0).setCellValue(analise.getGenero());
        	
	            
        }
 
        
        return workbook;
    }

	

	
	private List<String> createLista(List<Analise> analises) {
		ArrayList<String> lista = new ArrayList<String>();
        
        lista.add("Código");
        
        if(analises.get(0).getSabor()!=0) {
        	 lista.add("Sabor");
        }
        if(analises.get(0).getAroma()!=0) {
        	lista.add("Aroma");
        }
        if(analises.get(0).getAparencia()!=0) {
        	lista.add("Aparencia");
      	}
        if(analises.get(0).getSaborresidual()!=0) {
        	lista.add("Sabor Residual");
        }
        if(analises.get(0).getDocura()!=0) {
        	 lista.add("Doçura");
        }
        if(analises.get(0).getTextura()!=0) {
        	lista.add("Textura");
        }
        if(analises.get(0).getOdor()!=0) {
        	lista.add("Odor");
        }
        if(analises.get(0).getMaciez()!=0) {
        	lista.add("Maciez");
        }
        if(analises.get(0).getConsistencia()!=0) {
        	lista.add("Consistencia");
        }
        if(analises.get(0).getAvalglobal()!=0) {
        	lista.add("Avaliação Global");
        }
        lista.add("Intenção de Compra");
       
        
        
        return lista;
	}

	private ArrayList<String> createCategoria(List<Analise> analises) {
		 
			ArrayList<String> categorias = new ArrayList<String>();
			
			for (Analise analise: analises) {
	        	if(analise.getCategoria().equals("A") && !categorias.contains("Categoria A")) {
	        		categorias.add("Categoria A");
	        	}
	        }
	        
	        for (Analise analise: analises) {
	        	if(analise.getCategoria().equals("B") && !categorias.contains("Categoria B")) {
	        		categorias.add("Categoria B");
	        	}
	        }
	        for (Analise analise: analises) {
	        	if(analise.getCategoria().equals("C") && !categorias.contains("Categoria C")) {
	        		categorias.add("Categoria C");
	        	}
	        }
	        
	        for (Analise analise: analises) {
	        	if(analise.getCategoria().equals("D") && !categorias.contains("Categoria D")) {
	        		categorias.add("Categoria D");
	        	}
	        }
	        
	        for (Analise analise: analises) {
	        	if(analise.getCategoria().equals("E") && !categorias.contains("Categoria E")) {
	        		categorias.add("Categoria E");
	        	}
	        	
	        }
	        
	        return categorias;
	}

	
	
	

}

