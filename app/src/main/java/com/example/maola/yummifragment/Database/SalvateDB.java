package com.example.maola.yummifragment.Database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Unique;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

/**
 * Created by Maola on 18/07/2017.
 */


@Entity
public class SalvateDB {

    @Id(autoincrement = true)
    @Unique
    private Long idSalvate;

    private Long categoriaId;
    @ToOne(joinProperty = "categoriaId")
    private CategorieDB categorieDB;

    @Property
    private String titoloRicettaSalvata;

    @Property
    private int personeOriginali;

    @Property
    private int personeFinali;

    @Property
    private String corpoRicettaSalvata;

    @Property
    private String note;

    @Property
    private String corpoRicettaOriginale;

    @Property
    private Date datetime;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 447684062)
    private transient SalvateDBDao myDao;

    @Generated(hash = 911844134)
    public SalvateDB(Long idSalvate, Long categoriaId, String titoloRicettaSalvata,
            int personeOriginali, int personeFinali, String corpoRicettaSalvata,
            String note, String corpoRicettaOriginale, Date datetime) {
        this.idSalvate = idSalvate;
        this.categoriaId = categoriaId;
        this.titoloRicettaSalvata = titoloRicettaSalvata;
        this.personeOriginali = personeOriginali;
        this.personeFinali = personeFinali;
        this.corpoRicettaSalvata = corpoRicettaSalvata;
        this.note = note;
        this.corpoRicettaOriginale = corpoRicettaOriginale;
        this.datetime = datetime;
    }

    @Generated(hash = 954520926)
    public SalvateDB() {
    }

    public Long getIdSalvate() {
        return this.idSalvate;
    }

    public void setIdSalvate(Long idSalvate) {
        this.idSalvate = idSalvate;
    }

    public Long getCategoriaId() {
        return this.categoriaId;
    }

    public void setCategoriaId(Long categoriaId) {
        this.categoriaId = categoriaId;
    }

    public String getTitoloRicettaSalvata() {
        return this.titoloRicettaSalvata;
    }

    public void setTitoloRicettaSalvata(String titoloRicettaSalvata) {
        this.titoloRicettaSalvata = titoloRicettaSalvata;
    }

    public int getPersoneOriginali() {
        return this.personeOriginali;
    }

    public void setPersoneOriginali(int personeOriginali) {
        this.personeOriginali = personeOriginali;
    }

    public int getPersoneFinali() {
        return this.personeFinali;
    }

    public void setPersoneFinali(int personeFinali) {
        this.personeFinali = personeFinali;
    }

    public String getCorpoRicettaSalvata() {
        return this.corpoRicettaSalvata;
    }

    public void setCorpoRicettaSalvata(String corpoRicettaSalvata) {
        this.corpoRicettaSalvata = corpoRicettaSalvata;
    }

    public String getNote() {
        return this.note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getCorpoRicettaOriginale() {
        return this.corpoRicettaOriginale;
    }

    public void setCorpoRicettaOriginale(String corpoRicettaOriginale) {
        this.corpoRicettaOriginale = corpoRicettaOriginale;
    }

    public Date getDatetime() {
        return this.datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    @Generated(hash = 1007808070)
    private transient Long categorieDB__resolvedKey;

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 531940366)
    public CategorieDB getCategorieDB() {
        Long __key = this.categoriaId;
        if (categorieDB__resolvedKey == null
                || !categorieDB__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            CategorieDBDao targetDao = daoSession.getCategorieDBDao();
            CategorieDB categorieDBNew = targetDao.load(__key);
            synchronized (this) {
                categorieDB = categorieDBNew;
                categorieDB__resolvedKey = __key;
            }
        }
        return categorieDB;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 797269227)
    public void setCategorieDB(CategorieDB categorieDB) {
        synchronized (this) {
            this.categorieDB = categorieDB;
            categoriaId = categorieDB == null ? null : categorieDB.getId();
            categorieDB__resolvedKey = categoriaId;
        }
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 474610927)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getSalvateDBDao() : null;
    }
}