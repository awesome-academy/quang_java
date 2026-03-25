package com.sun.booking;


import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.sun.booking.tours.Tour;
import com.sun.booking.tours.dto.TourDTO;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

@Configuration
public class AppConfig {
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public OpenAPI customOpenAPI() {
    final String securitySchemeName = "bearerAuth";

    return new OpenAPI()
      .addSecurityItem(new SecurityRequirement().addList(securitySchemeName)) // 👈 QUAN TRỌNG
      .components(new Components()
        .addSecuritySchemes(securitySchemeName,
          new SecurityScheme()
                  .type(SecurityScheme.Type.HTTP)
                  .scheme("bearer")
                  .bearerFormat("JWT")
        )
      );
  }

  @Bean
  public ModelMapper modelMapper() {
    return new ModelMapper() {
      @Override
      public <D> D map(Object source, Class<D> destinationType) {
        D destination = super.map(source, destinationType);
        if(source instanceof Tour && destination instanceof TourDTO) {
          Tour tour = (Tour) source;
          TourDTO tourDTO = (TourDTO) destination;
          tourDTO.setCategoryId(tour.getCategory().getId());
          tourDTO.setTitle(tour.getTitle());
          tourDTO.setDescription(tour.getDescription());
          tourDTO.setPrice(tour.getPrice());
          tourDTO.setRating(tour.getRating());
          tourDTO.setReviewsCount(tour.getReviewsCount());
        }
        return destination;
      }
    };
  }

  @Bean
  public HttpSessionSecurityContextRepository httpSessionSecurityContextRepository() {
    return new HttpSessionSecurityContextRepository();
  }
}
