package com.sun.booking;

import java.math.BigDecimal;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.sun.booking.tours.Tour;
import com.sun.booking.tours.dto.TourDTO;
import com.sun.booking.tours.dto.TourShortDto;
import com.sun.booking.userreviews.dto.UserReviewDTO;
import com.sun.booking.userreviews.entity.UserReview;
import com.sun.booking.users.User;
import com.sun.booking.users.dto.UserDTO;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

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
          tourDTO.setId(tour.getId());
          tourDTO.setCategoryId(tour.getCategory().getId());
          tourDTO.setTitle(tour.getTitle());
          tourDTO.setDescription(tour.getDescription());
          tourDTO.setPrice(tour.getPrice());
          tourDTO.setRating(tour.getRating());
          tourDTO.setReviewsCount(tour.getReviewsCount());
        }

        if(source instanceof User && destination instanceof UserDTO) {
          User user = (User) source;
          UserDTO userDTO = (UserDTO) destination;
          userDTO.setUsername(user.getUsername());
          userDTO.setEmail(user.getEmail());
          userDTO.setSocialId(user.getSocialId());
          userDTO.setSocialType(user.getSocialType());
        }

        if(source instanceof UserReview && destination instanceof UserReviewDTO) {
          UserReview review = (UserReview) source;
          UserReviewDTO reviewDTO = (UserReviewDTO) destination;
          reviewDTO.setUserId(review.getUser().getId());
          TourShortDto tourShortDto = new TourShortDto();
          tourShortDto.setCategoryId(review.getTour().getCategory().getId());
          tourShortDto.setTitle(review.getTour().getTitle());
          tourShortDto.setDescription(review.getTour().getDescription());
          tourShortDto.setPrice(review.getTour().getPrice());
          reviewDTO.setTour(tourShortDto);
          reviewDTO.setRating(review.getRating());
          reviewDTO.setContent(review.getContent());
        }

        return destination;
      }
    };
  }
}
