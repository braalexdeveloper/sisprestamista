package com.brayanweb.sisprestamistas.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.brayanweb.sisprestamistas.dtos.ClientRequest;
import com.brayanweb.sisprestamistas.dtos.ClientResponse;
import com.brayanweb.sisprestamistas.exceptions.ResourceNotFoundException;
import com.brayanweb.sisprestamistas.models.Client;
import com.brayanweb.sisprestamistas.models.User;
import com.brayanweb.sisprestamistas.repositories.ClientRepository;
import com.brayanweb.sisprestamistas.repositories.UserRepository;

@Service
public class ClientService {

    private final ClientRepository clientRepository;
    private final UserRepository userRepository;

    public ClientService(ClientRepository clientRepository, UserRepository userRepository) {
        this.clientRepository = clientRepository;
        this.userRepository = userRepository;
    }

    public List<ClientResponse> getClients() {
        List<Client> clients = clientRepository.findAll();
        List<ClientResponse> clientsResponse = new ArrayList<>();

        for (Client client : clients) {
            clientsResponse.add(convertToClientResponse(client));
        }

        return clientsResponse;
    }

    public ClientResponse getClient(Long id) {
        Client client = clientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado"));
        return convertToClientResponse(client);
    }

    public ClientResponse create(ClientRequest clientRequest) {
        User user = userRepository.findById(clientRequest.getUserId()).orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        Client client = clientRepository.save(convertToClient(clientRequest, new Client(), user));
        return convertToClientResponse(client);
    }

    public ClientResponse update(ClientRequest clientRequest, Long id) {
        Client clientFound = clientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado"));

        User user = userRepository.findById(clientRequest.getUserId()).orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        Client clientUpdate = convertToClient(clientRequest, clientFound, user);
        clientUpdate = clientRepository.save(clientUpdate);

        return convertToClientResponse(clientUpdate);
    }

    public String delete(Long id) {
        if (!clientRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cliente no encontrado");
        }
        clientRepository.deleteById(id);
        return "Cliente Eliminado";
    }

    private Client convertToClient(ClientRequest clientRequest, Client client, User user) {
        client.setDni(clientRequest.getDni());
        client.setAddress(clientRequest.getAddress());
        client.setPoliceRecord(clientRequest.getPoliceRecord());
        client.setPhoto(clientRequest.getPhoto());
        client.setUser(user);
        return client;
    }

    private ClientResponse convertToClientResponse(Client client) {
        ClientResponse clientResponse = new ClientResponse();
        clientResponse.setId(client.getId());
        clientResponse.setName(client.getUser().getName());
        clientResponse.setLastName(client.getUser().getLastName());
        clientResponse.setDni(client.getDni());
        clientResponse.setAddress(client.getAddress());
        clientResponse.setPoliceRecord(client.getPoliceRecord());
        clientResponse.setPhoto(client.getPhoto());
        return clientResponse;
    }
}
