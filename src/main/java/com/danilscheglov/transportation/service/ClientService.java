package com.danilscheglov.transportation.service;

import com.danilscheglov.transportation.dto.ClientDTO;
import com.danilscheglov.transportation.entity.Client;
import com.danilscheglov.transportation.exception.ResourceNotFoundException;
import com.danilscheglov.transportation.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Client getClientById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Клиент с ID " + id + " не найден"));
    }

    @Transactional
    public Client createClient(ClientDTO clientDTO) {
        Client client = Client.builder()
                .surname(clientDTO.getSurname())
                .name(clientDTO.getName())
                .patronymic(clientDTO.getPatronymic())
                .phone(clientDTO.getPhone())
                .email(clientDTO.getEmail())
                .password(clientDTO.getPassword())
                .build();
        return clientRepository.save(client);
    }

    @Transactional
    public Client updateClient(Long id, ClientDTO clientDTO) {
        Client existingClient = getClientById(id);
        existingClient.setSurname(clientDTO.getSurname());
        existingClient.setName(clientDTO.getName());
        existingClient.setPatronymic(clientDTO.getPatronymic());
        existingClient.setPhone(clientDTO.getPhone());
        existingClient.setEmail(clientDTO.getEmail());
        if (clientDTO.getPassword() != null && !clientDTO.getPassword().isEmpty()) {
            existingClient.setPassword(clientDTO.getPassword());
        }
        return clientRepository.save(existingClient);
    }

    @Transactional
    public void deleteClient(Long id) {
        if (!clientRepository.existsById(id)) {
            throw new ResourceNotFoundException("Клиент с ID " + id + " не найден");
        }
        clientRepository.deleteById(id);
    }
}