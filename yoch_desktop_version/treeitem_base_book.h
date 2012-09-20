﻿#ifndef TREEITEM_BASE_BOOK_H
#define TREEITEM_BASE_BOOK_H


#include <QPointer>

#include "treeitem_base.h"
//#include "bookdisplayer.h"

//class NodeBook;


class NodeBook : public BaseNodeItem
{
public:
    enum Booktype {
        Orayta,
        Html,
        Pdf,
        Link,
        Unkown
    };

    NodeBook(BaseNodeItem *parent, QString path, QString name, bool isUserBook);

    //getters
    Nodetype nodetype() const;
    BookDisplayer* tabWidget() const;
    int getUniqueId() const;
    int getRealUniqueId() const;

    virtual bool isFontModifiable() const;
    virtual bool isSearchable() const;
    virtual Booktype booktype() const = 0;
    virtual QList<QAction*> menuActions() const;
    virtual QString getLoadableFile() const;

    // setters
    void setTabWidget(BookDisplayer*);
    void setRandomUniqueId(int id);

    static NodeBook* BookFactory(BaseNodeItem *parent, QString path, QString name, Booktype ft, bool isUserBook);


protected:
    virtual void AddConfs();

    //QWidget* of current viewTab (0 if not exists)
    QPointer<BookDisplayer> mTabWidget;

    //Unique id used for linking to a specific book
    int mUniqueId;

    int mRandomId;
};


typedef QList<NodeBook*> BookList;


#endif // TREEITEM_BASE_BOOK_H