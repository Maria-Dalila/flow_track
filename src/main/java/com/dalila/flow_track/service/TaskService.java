package com.dalila.flow_track.service;

import com.dalila.flow_track.dto.taskDTO.TaskAssignmentDTO;
import com.dalila.flow_track.dto.taskDTO.TaskPersonalDTO;
import com.dalila.flow_track.dto.taskDTO.TaskResponseDTO;
import com.dalila.flow_track.dto.userDTO.UserResponseDTO;

import java.util.List;
import java.util.UUID;

public interface TaskService {

   /** O gpt disse que pra eu pegar os dados do usuario para fazer a verificação la eu posso usar o objeto security context holder
    *
    * @Service
    public class SeuService {

        public void seuMetodo() {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication != null && authentication.isAuthenticated()) {
                String username = authentication.getName();
                // Outras operações com as informações do usuário
            }
        }
    }
    vou criar esse metodo que retorna um boolean avisando se o user pode ou não alterar esse dado
    public boolean isenable(UUID userAuthenticatedId, UUID taskId);**/

    public TaskResponseDTO createPersonalTask(TaskPersonalDTO taskPersonal);
    public List<TaskResponseDTO> findAllPersonalTasks();
    public TaskResponseDTO updatePersonalTask(TaskResponseDTO task);
    public void deletePersonalTask(UUID taskId);
    public TaskResponseDTO updateStatusTask(String status);
    public List<TaskResponseDTO> findTaskByTitle(String title);

    public boolean createTaskAssignment(TaskAssignmentDTO taskAssignment);
    /**frizando uma coisa sobre esse metodo acima, testanto sem o front temos que popular a lista na mão, mas
     * o user na interface so sistema vai ter a opção de buscar usuario por nome quando for popular a lista.*/



}
