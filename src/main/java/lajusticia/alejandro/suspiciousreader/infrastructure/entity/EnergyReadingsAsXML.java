package lajusticia.alejandro.suspiciousreader.infrastructure.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@ToString
@XmlRootElement(name = "readings")
@XmlAccessorType(XmlAccessType.FIELD)
public class EnergyReadingsAsXML implements Serializable {

    @XmlElement(name = "reading")
    private List<EnergyReadingAsXML> readings;

}
