package at.technikumwien.lernbegleiter.entities.base;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.util.UUID;

public class UuidGenerator implements IdentifierGenerator {
  @Override
  public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
    BaseEntity<?> be = (BaseEntity) object;

    if (be.getUuid() == null || be.getUuid().length() == 0) {
      return UUID.randomUUID().toString();
    }
    return be.getUuid();
  }
}
