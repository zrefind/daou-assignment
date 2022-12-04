package org.kang.assignment.util.converter;

import org.kang.assignment.domain.member.Role;
import org.kang.assignment.domain.member.RoleSet;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Converter
public class RoleSetConverter implements AttributeConverter<RoleSet, String> {

    @Override
    public String convertToDatabaseColumn(RoleSet attribute) {
        if (attribute == null) return null;

        return attribute.getRolesReadOnly().stream()
                .map(Role::name)
                .collect(Collectors.joining(","));
    }

    @Override
    public RoleSet convertToEntityAttribute(String dbData) {
        if (dbData == null) return null;

        String[] roles = dbData.split(",");
        Set<Role> roleSet = Arrays.stream(roles)
                .map(Role::valueOf)
                .collect(Collectors.toSet());

        return new RoleSet(roleSet);
    }

}
