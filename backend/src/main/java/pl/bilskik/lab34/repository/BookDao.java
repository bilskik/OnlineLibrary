//package pl.bilskik.lab34.repository;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.stereotype.Repository;
//import pl.bilskik.lab34.entities.Book;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Repository
//public class BookDao {
//
//    public static final String HASH_KEY = "Book";
//    @Autowired
//    private RedisTemplate template;
//    public List<Book> findAll() {
//        List<Object> list =  template.opsForHash().values(HASH_KEY);
//        List<Book> bookList = new ArrayList<>();
//        for (Object o : list) {
//            if (o instanceof Book) {
//                bookList.add((Book) o);
//            }
//        }
//        return bookList;
//    }
//    public Book findBookById(Long productId) {
//        return (Book)template.opsForHash().get(HASH_KEY, productId);
//    }
//
//    public void saveBook(Book book) {
//        System.out.println(book.getBookId() + " " + book.getName());
////        book.setBookId(1L);
//        template.opsForHash().put(HASH_KEY,book.getBookId(), book);
//    }
//    public void deleteBookById(Long productId) {
//        template.opsForHash().delete(HASH_KEY, productId);
//    }
//    public void updateBook(Book book) {
//        template.opsForHash().put(HASH_KEY, book.getBookId(), book);
//    }
//}
//
