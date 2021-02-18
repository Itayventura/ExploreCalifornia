package com.itayvenutra.exploreCalifornia.domain;

import com.itayvenutra.exploreCalifornia.domain.Region;
import com.itayvenutra.exploreCalifornia.domain.RegionConverter;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class RegionConverterTest {
    private RegionConverter converter = new RegionConverter();
    @Test
    public void convertToDatabaseColumn() throws Exception {
        assertThat(converter.convertToDatabaseColumn(Region.Central_Coast), is(Region.Central_Coast.getLabel()));
    }

    @Test
    public void convertToEntityAttribute() throws Exception {
        assertThat(converter.convertToEntityAttribute(Region.Central_Coast.getLabel()), is(Region.Central_Coast));
    }

}