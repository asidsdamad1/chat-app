package org.asi.authservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Immutable;

import java.io.Serializable;

@ToString
@Entity
@Table(schema = "role")
@Getter
@Setter
@NoArgsConstructor
@Immutable
public class Role implements Serializable {

    public static final long serialVersionUID = -8053205789790776096L;

    @Id
    @Column(length = 50, nullable = false, unique = true)
    private String name;

}
