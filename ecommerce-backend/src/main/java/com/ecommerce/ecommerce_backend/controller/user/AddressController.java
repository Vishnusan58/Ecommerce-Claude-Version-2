package com.ecommerce.ecommerce_backend.controller.user;

import com.ecommerce.ecommerce_backend.dto.address.AddressRequestDTO;
import com.ecommerce.ecommerce_backend.dto.address.AddressResponseDTO;
import com.ecommerce.ecommerce_backend.model.User;
import com.ecommerce.ecommerce_backend.model.UserAddress;
import com.ecommerce.ecommerce_backend.service.auth.AuthService;
import com.ecommerce.ecommerce_backend.service.user.AddressService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user/addresses")
public class AddressController {

    private final AddressService addressService;
    private final AuthService authService;

    public AddressController(AddressService addressService,
                             AuthService authService) {
        this.addressService = addressService;
        this.authService = authService;
    }

    @PostMapping
    public AddressResponseDTO addAddress(
            @RequestHeader("X-USER-ID") Long userId,
            @RequestBody AddressRequestDTO dto) {

        User user = authService.getUserById(userId);

        UserAddress address = new UserAddress();
        address.setAddressLine1(dto.getAddressLine1());
        address.setAddressLine2(dto.getAddressLine2());
        address.setCity(dto.getCity());
        address.setState(dto.getState());
        address.setPincode(dto.getPincode());
        address.setDefault(dto.isDefault());

        UserAddress saved = addressService.addAddress(user, address);
        return mapToDto(saved);
    }

    @GetMapping
    public List<AddressResponseDTO> getAddresses(
            @RequestHeader("X-USER-ID") Long userId) {

        User user = authService.getUserById(userId);

        return addressService.getAddresses(user)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private AddressResponseDTO mapToDto(UserAddress address) {
        AddressResponseDTO dto = new AddressResponseDTO();
        dto.setId(address.getId());
        dto.setAddressLine1(address.getAddressLine1());
        dto.setAddressLine2(address.getAddressLine2());
        dto.setCity(address.getCity());
        dto.setState(address.getState());
        dto.setPincode(address.getPincode());
        dto.setDefault(address.isDefault());
        return dto;
    }
}
