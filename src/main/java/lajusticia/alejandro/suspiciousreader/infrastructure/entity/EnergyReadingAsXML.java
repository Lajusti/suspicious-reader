package lajusticia.alejandro.suspiciousreader.infrastructure.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.xml.bind.annotation.*;
import java.io.Serializable;

@Getter
@Setter
@ToString
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="reading")
public class EnergyReadingAsXML implements Serializable {

    @XmlValue
    private long reading;

    @XmlAttribute(name = "clientID")
    private String clientID;

    @XmlAttribute(name = "period")
    private String period;

}
