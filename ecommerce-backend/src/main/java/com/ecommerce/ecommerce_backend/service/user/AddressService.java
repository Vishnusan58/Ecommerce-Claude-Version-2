package com.ecommerce.ecommerce_backend.service.user;

import com.ecommerce.ecommerce_backend.model.User;
import com.ecommerce.ecommerce_backend.model.UserAddress;
import com.ecommerce.ecommerce_backend.repository.UserAddressRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {

    private final UserAddressRepository addressRepository;

    public AddressService(UserAddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public UserAddress addAddress(User user, UserAddress address) {
        address.setUser(user);
        return addressRepository.save(address);
    }

    // Used by OrderController for inline addresses
    public UserAddress saveAddress(UserAddress address) {
        return addressRepository.save(address);
    }

    public List<UserAddress> getAddresses(User user) {
        return addressRepository.findByUser(user);
    }

    public UserAddress updateAddress(User user, Long addressId, UserAddress updated) {
        UserAddress address = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found"));

        if (!address.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized access");
        }

        address.setFullName(updated.getFullName());
        address.setPhone(updated.getPhone());
        address.setAddressLine1(updated.getAddressLine1());
        address.setAddressLine2(updated.getAddressLine2());
        address.setCity(updated.getCity());
        address.setState(updated.getState());
        address.setPincode(updated.getPincode());
        address.setCountry(updated.getCountry());
        address.setDefault(updated.isDefault());

        return addressRepository.save(address);
    }

    public void deleteAddress(User user, Long addressId) {
        UserAddress address = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found"));

        if (!address.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized access");
        }

        addressRepository.delete(address);
    }
}
