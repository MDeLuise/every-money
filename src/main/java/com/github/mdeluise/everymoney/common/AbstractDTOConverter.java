package com.github.mdeluise.everymoney.common;

public abstract class AbstractDTOConverter<D, T> {
    public abstract D convertFromDTO(T dto);

    public abstract T convertToDTO(D data);
}
