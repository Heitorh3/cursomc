package br.com.heitor.cursomc.service.validator;

import br.com.heitor.cursomc.domain.Cliente;
import br.com.heitor.cursomc.domain.TipoCliente;
import br.com.heitor.cursomc.dto.ClienteNewDTO;
import br.com.heitor.cursomc.repository.ClienteRepository;
import br.com.heitor.cursomc.service.exceptions.FieldMessage;
import br.com.heitor.cursomc.service.validator.util.BR;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;


public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {

    @Autowired
    private ClienteRepository repo;

   public void initialize(ClienteInsert constraint) {
   }

   public boolean isValid(ClienteNewDTO obj, ConstraintValidatorContext context) {
      List<FieldMessage> list = new ArrayList<>();

       if (obj.getTipoCliente().equals(TipoCliente.PESSOAFISICA) && !BR.isValidCPF(obj.getCpfOuCnpj())) {
           list.add(new FieldMessage("cpfOuCnpj", "CPF inválido"));
       }

       if (obj.getTipoCliente().equals(TipoCliente.PESSOAJURIDICA) && !BR.isValidCNPJ(obj.getCpfOuCnpj())) {
           list.add(new FieldMessage("cpfOuCnpj", "CNPJ inválido"));
       }

       Cliente aux = repo.findByEmail(obj.getEmail());
       if (aux != null) {
           list.add(new FieldMessage("email", "Email já existente"));
       }

      for (FieldMessage e : list) {
         context.disableDefaultConstraintViolation();
         context.buildConstraintViolationWithTemplate(e.getMessage())
                 .addPropertyNode(e.getFieldName()).addConstraintViolation();
      }
      return list.isEmpty();
   }
}
